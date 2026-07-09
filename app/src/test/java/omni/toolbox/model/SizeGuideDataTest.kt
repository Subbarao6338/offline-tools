package omni.toolbox.model

import org.junit.Assert.assertTrue
import org.junit.Test

class SizeGuideDataTest {
    @Test
    fun testSizeGuideDataNotEmpty() {
        assertTrue(SizeGuideData.womenCategories.isNotEmpty())
        assertTrue(SizeGuideData.menCategories.isNotEmpty())
        assertTrue(SizeGuideData.kidsCategories.isNotEmpty())
        assertTrue(SizeGuideData.innerwearCategories.isNotEmpty())
    }

    @Test
    fun testSizeChartConsistency() {
        val allCharts = SizeGuideData.womenCategories +
                         SizeGuideData.menCategories +
                         SizeGuideData.kidsCategories +
                         SizeGuideData.innerwearCategories

        for (chart in allCharts) {
            val columnCount = chart.columns.size
            for (row in chart.rows) {
                assertTrue("Chart ${chart.title} has inconsistent row size", row.values.size == columnCount)
            }
        }
    }
}
