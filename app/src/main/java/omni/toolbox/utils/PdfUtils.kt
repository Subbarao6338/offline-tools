package omni.toolbox.utils

import android.graphics.Bitmap
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.rendering.PDFRenderer
import java.io.File
import java.io.FileOutputStream
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState
import com.tom_roush.pdfbox.pdmodel.graphics.blend.BlendMode
import com.tom_roush.pdfbox.pdmodel.graphics.image.LosslessFactory
import com.tom_roush.pdfbox.pdmodel.common.PDRectangle
import com.tom_roush.pdfbox.pdmodel.graphics.image.JPEGFactory
import android.content.Context
import android.net.Uri
import android.graphics.BitmapFactory
import com.tom_roush.pdfbox.pdmodel.font.PDType1Font

object PdfUtils {
    fun exportToImages(pdfFile: File, outputDir: File): List<File> {
        if (!outputDir.exists()) outputDir.mkdirs()
        val document = PDDocument.load(pdfFile)
        val renderer = PDFRenderer(document)
        val exportedFiles = mutableListOf<File>()

        for (i in 0 until document.numberOfPages) {
            val bitmap = renderer.renderImageWithDPI(i, 300f)
            val outFile = File(outputDir, "page_${i + 1}.jpg")
            FileOutputStream(outFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }
            exportedFiles.add(outFile)
        }
        document.close()
        return exportedFiles
    }

    fun protect(pdfFile: File, password: String, outFile: File) {
        val document = PDDocument.load(pdfFile)
        val accessPermissions = com.tom_roush.pdfbox.pdmodel.encryption.AccessPermission()
        val standardProtection = com.tom_roush.pdfbox.pdmodel.encryption.StandardProtectionPolicy(password, password, accessPermissions)
        standardProtection.encryptionKeyLength = 128
        document.protect(standardProtection)
        document.save(outFile)
        document.close()
    }

    fun rotatePages(pdfFile: File, angle: Int, outFile: File) {
        val document = PDDocument.load(pdfFile)
        for (page in document.pages) {
            page.rotation = (page.rotation + angle) % 360
        }
        document.save(outFile)
        document.close()
    }

    fun unlock(pdfFile: File, password: String, outFile: File) {
        val document = PDDocument.load(pdfFile, password)
        document.isAllSecurityToBeRemoved = true
        document.save(outFile)
        document.close()
    }

    fun repair(pdfFile: File, outFile: File) {
        val document = PDDocument.load(pdfFile)
        document.save(outFile)
        document.close()
    }

    fun invert(pdfFile: File, outFile: File) {
        val document = PDDocument.load(pdfFile)
        for (page in document.pages) {
            PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true).use { contentStream ->
                val graphicsState = PDExtendedGraphicsState()
                graphicsState.blendMode = BlendMode.DIFFERENCE
                contentStream.setGraphicsStateParameters(graphicsState)
                contentStream.setNonStrokingColor(1f, 1f, 1f)
                contentStream.addRect(0f, 0f, page.mediaBox.width, page.mediaBox.height)
                contentStream.fill()
            }
        }
        document.save(outFile)
        document.close()
    }

    fun flatten(pdfFile: File, outFile: File) {
        val document = PDDocument.load(pdfFile)
        document.documentCatalog.acroForm?.flatten()
        document.save(outFile)
        document.close()
    }

    fun grayscale(pdfFile: File, outFile: File) {
        val document = PDDocument.load(pdfFile)
        val renderer = PDFRenderer(document)
        val newDoc = PDDocument()
        for (i in 0 until document.numberOfPages) {
            val bitmap = renderer.renderImageWithDPI(i, 200f)
            // Convert to grayscale using Android Bitmap logic
            val grayBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(grayBitmap)
            val paint = android.graphics.Paint()
            val colorMatrix = android.graphics.ColorMatrix()
            colorMatrix.setSaturation(0f)
            paint.colorFilter = android.graphics.ColorMatrixColorFilter(colorMatrix)
            canvas.drawBitmap(bitmap, 0f, 0f, paint)

            val page = com.tom_roush.pdfbox.pdmodel.PDPage(PDRectangle(bitmap.width.toFloat(), bitmap.height.toFloat()))
            newDoc.addPage(page)
            val pdImage = LosslessFactory.createFromImage(newDoc, grayBitmap)
            PDPageContentStream(newDoc, page).use { contentStream ->
                contentStream.drawImage(pdImage, 0f, 0f)
            }
        }
        newDoc.save(outFile)
        newDoc.close()
        document.close()
    }

    fun compress(pdfFile: File, outFile: File) {
        val document = PDDocument.load(pdfFile)
        val renderer = PDFRenderer(document)
        val newDoc = PDDocument()
        for (i in 0 until document.numberOfPages) {
            val bitmap = renderer.renderImageWithDPI(i, 150f)
            val page = com.tom_roush.pdfbox.pdmodel.PDPage(document.getPage(i).mediaBox)
            newDoc.addPage(page)
            val pdImage = JPEGFactory.createFromImage(newDoc, bitmap, 0.6f)
            PDPageContentStream(newDoc, page).use { contentStream ->
                contentStream.drawImage(pdImage, 0f, 0f, page.mediaBox.width, page.mediaBox.height)
            }
        }
        newDoc.save(outFile)
        newDoc.close()
        document.close()
    }

    fun imagesToPdf(context: Context, images: List<Uri>, outFile: File) {
        val document = PDDocument()
        images.forEach { uri ->
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream) ?: return@use
                try {
                    val page = com.tom_roush.pdfbox.pdmodel.PDPage(PDRectangle(bitmap.width.toFloat(), bitmap.height.toFloat()))
                    document.addPage(page)
                    val pdImage = LosslessFactory.createFromImage(document, bitmap)
                    PDPageContentStream(document, page).use { contentStream ->
                        contentStream.drawImage(pdImage, 0f, 0f)
                    }
                } finally {
                    bitmap.recycle()
                }
            }
        }
        document.save(outFile)
        document.close()
    }

    fun removePages(pdfFile: File, pageRanges: String, outFile: File) {
        val document = PDDocument.load(pdfFile)
        val pagesToRemove = parsePageRanges(pageRanges, document.numberOfPages)
        val newDoc = PDDocument()
        for (i in 0 until document.numberOfPages) {
            if (!pagesToRemove.contains(i + 1)) {
                newDoc.importPage(document.getPage(i))
            }
        }
        newDoc.save(outFile)
        newDoc.close()
        document.close()
    }

    private fun parsePageRanges(ranges: String, maxPages: Int): Set<Int> {
        val result = mutableSetOf<Int>()
        ranges.split(",").forEach { part ->
            val trimmed = part.trim()
            if (trimmed.contains("-")) {
                val bounds = trimmed.split("-")
                if (bounds.size == 2) {
                    val start = bounds[0].trim().toIntOrNull() ?: 1
                    val end = bounds[1].trim().toIntOrNull() ?: maxPages
                    for (i in start..end) result.add(i)
                }
            } else {
                trimmed.toIntOrNull()?.let { result.add(it) }
            }
        }
        return result
    }

    fun addPageNumbers(pdfFile: File, outFile: File) {
        val document = PDDocument.load(pdfFile)
        val font = PDType1Font.HELVETICA
        val fontSize = 10f

        for (i in 0 until document.numberOfPages) {
            val page = document.getPage(i)
            PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true).use { contentStream ->
                contentStream.beginText()
                contentStream.setFont(font, fontSize)
                val text = "Page ${i + 1} of ${document.numberOfPages}"
                val textWidth = font.getStringWidth(text) / 1000f * fontSize
                contentStream.newLineAtOffset((page.mediaBox.width - textWidth) / 2, 20f)
                contentStream.showText(text)
                contentStream.endText()
            }
        }
        document.save(outFile)
        document.close()
    }

    fun addWatermark(pdfFile: File, watermarkText: String, outFile: File) {
        val document = PDDocument.load(pdfFile)
        val font = PDType1Font.HELVETICA_BOLD
        val fontSize = 60f

        for (page in document.pages) {
            PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true).use { contentStream ->
                val graphicsState = PDExtendedGraphicsState()
                graphicsState.nonStrokingAlphaConstant = 0.3f
                contentStream.setGraphicsStateParameters(graphicsState)
                contentStream.setNonStrokingColor(200, 200, 200)
                contentStream.beginText()
                contentStream.setFont(font, fontSize)

                // Position at center with rotation
                contentStream.setTextMatrix(com.tom_roush.pdfbox.util.Matrix.getRotateInstance(Math.toRadians(45.0), page.mediaBox.width / 2, page.mediaBox.height / 2))
                val textWidth = font.getStringWidth(watermarkText) / 1000f * fontSize
                contentStream.newLineAtOffset(-textWidth / 2, 0f)
                contentStream.showText(watermarkText)
                contentStream.endText()
            }
        }
        document.save(outFile)
        document.close()
    }

    fun cropPdf(pdfFile: File, outFile: File) {
        val document = PDDocument.load(pdfFile)
        for (page in document.pages) {
            val mediaBox = page.mediaBox
            val newWidth = mediaBox.width * 0.8f
            val newHeight = mediaBox.height * 0.8f
            val x = (mediaBox.width - newWidth) / 2
            val y = (mediaBox.height - newHeight) / 2
            page.cropBox = PDRectangle(x, y, newWidth, newHeight)
        }
        document.save(outFile)
        document.close()
    }

    fun zipPdf(pdfFile: File, outFile: File) {
        java.util.zip.ZipOutputStream(java.io.FileOutputStream(outFile)).use { zos ->
            val entry = java.util.zip.ZipEntry(pdfFile.name)
            zos.putNextEntry(entry)
            pdfFile.inputStream().use { it.copyTo(zos) }
            zos.closeEntry()
        }
    }

    fun textToPdf(text: String, outFile: File) {
        val document = PDDocument()
        val font = PDType1Font.HELVETICA
        val fontSize = 12f
        val margin = 50f
        val leading = 1.5f * fontSize

        val sanitizedText = text.replace("\r", "")
        val lines = mutableListOf<String>()
        sanitizedText.split("\n").forEach { paragraph ->
            var currentLine = paragraph
            if (currentLine.isEmpty()) {
                lines.add("")
                return@forEach
            }
            while (currentLine.isNotEmpty()) {
                var length = 0f
                var lastSpace = -1
                var i = 0
                while (i < currentLine.length) {
                    val char = currentLine[i]
                    if (char == ' ') lastSpace = i

                    val charWidth = try {
                        font.getStringWidth(char.toString()) / 1000f * fontSize
                    } catch (e: Exception) {
                        font.getStringWidth("?") / 1000f * fontSize
                    }

                    val availableWidth = PDRectangle.A4.width - 2 * margin
                    if (length + charWidth > availableWidth) break
                    length += charWidth
                    i++
                }

                if (i == 0 && currentLine.isNotEmpty()) {
                    // Force at least one character to prevent infinite loop
                    i = 1
                }

                if (i < currentLine.length && lastSpace != -1) {
                    lines.add(currentLine.substring(0, lastSpace))
                    currentLine = currentLine.substring(lastSpace + 1)
                } else {
                    lines.add(currentLine.substring(0, i))
                    currentLine = currentLine.substring(i)
                }
            }
        }

        var page = com.tom_roush.pdfbox.pdmodel.PDPage(PDRectangle.A4)
        document.addPage(page)
        var contentStream = PDPageContentStream(document, page)
        contentStream.beginText()
        contentStream.setFont(font, fontSize)
        contentStream.newLineAtOffset(margin, page.mediaBox.upperRightY - margin)

        var currentY = page.mediaBox.upperRightY - margin

        lines.forEach { line ->
            if (currentY - leading < margin) {
                contentStream.endText()
                contentStream.close()
                page = com.tom_roush.pdfbox.pdmodel.PDPage(PDRectangle.A4)
                document.addPage(page)
                contentStream = PDPageContentStream(document, page)
                contentStream.beginText()
                contentStream.setFont(font, fontSize)
                contentStream.newLineAtOffset(margin, page.mediaBox.upperRightY - margin)
                currentY = page.mediaBox.upperRightY - margin
            }

            try {
                contentStream.showText(line)
            } catch (e: Exception) {
                // Fallback for non-mappable characters
                val fallbackLine = line.map { c ->
                    try { font.getStringWidth(c.toString()); c } catch (ex: Exception) { '?' }
                }.joinToString("")
                contentStream.showText(fallbackLine)
            }
            contentStream.newLineAtOffset(0f, -leading)
            currentY -= leading
        }

        contentStream.endText()
        contentStream.close()
        document.save(outFile)
        document.close()
    }
}
