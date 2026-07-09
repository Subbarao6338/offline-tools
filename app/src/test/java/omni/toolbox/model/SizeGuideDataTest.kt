package omni.toolbox.model

import org.junit.Assert.assertTrue
import org.junit.Test

class SizeGuideDataTest {
    @Test
    fun testSizeGuideDataNotEmpty() {
        assertTrue(SizeGuideData.womenCategories.isNotEmpty())
        assertTrue(SizeGuideData.menCategories.isNotEmpty())
        assertTrue(SizeGuideData.footwearCategories.isNotEmpty())
        assertTrue(SizeGuideData.accessoriesCategories.isNotEmpty())
        assertTrue(SizeGuideData.indianCategories.isNotEmpty())
        assertTrue(SizeGuideData.worldCategories.isNotEmpty())
        assertTrue(SizeGuideData.tribalCategories.isNotEmpty())
        assertTrue(SizeGuideData.modernCategories.isNotEmpty())
        assertTrue(SizeGuideData.globalConversion.isNotEmpty())
        assertTrue(SizeGuideData.innerwearCategories.isNotEmpty())
    }

    @Test
    fun testSizeChartConsistency() {
        val allCharts = SizeGuideData.womenCategories +
                         SizeGuideData.menCategories +
                         SizeGuideData.footwearCategories +
                         SizeGuideData.accessoriesCategories +
                         SizeGuideData.indianCategories +
                         SizeGuideData.worldCategories +
                         SizeGuideData.tribalCategories +
                         SizeGuideData.modernCategories +
                         SizeGuideData.globalConversion +
                         SizeGuideData.innerwearCategories

        for (chart in allCharts) {
            val columnCount = chart.columns.size
            for (row in chart.rows) {
                assertTrue("Chart ${chart.title} has inconsistent row size", row.values.size == columnCount)
            }
        }
    }
}
