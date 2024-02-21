package de.snowii.mastermind.ui.clickgui.elements.settings

import de.snowii.mastermind.settings.SettingInt
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.math.MathHelper
import java.awt.Color

class SettingsElementInt(private val settingInt: SettingInt, x: Int, y: Int) : SettingsElement(x, y) {
    private var sliderValue: Float
    private val colorSliderBox = Color(255, 200, 0, 200).rgb

    init {
        sliderValue = normalizeValue(settingInt, settingInt.value.toFloat())
    }

    override fun mouseDragged(context: DrawContext, mouseX: Int, mouseY: Int) {
        if (!settingInt.isVisible()) return
        if (dragging) {
            sliderValue = (mouseX - (x + 4)).toFloat() / (width * 2 - 8).toFloat()
            sliderValue = MathHelper.clamp(sliderValue, 0.0f, 1.0f)
            val f = denormalizeValue(settingInt, sliderValue)
            settingInt.value = f.toInt()
            sliderValue = normalizeValue(settingInt, f)
        }

        // Box
        context.fill(
            (x + 5),
            (y - 2),
            (x + 5 + width * 2),
            (y + height + 2),
            Color(0, 0, 0, 100).rgb
        )

        // Slider Box
        context.fill(
            (x + 5),
            (y - 2),
            (x + 5 + (sliderValue * (width * 2 - 8).toFloat()).toInt()),
            (y + height + 2),
            colorSliderBox
        )
    }

    override fun drawScreen(context: DrawContext, mouseX: Int, mouseY: Int) {
        if (!settingInt.isVisible()) return
        super.drawScreen(context, mouseX, mouseY)

        // Slider Box
        context.fill(
            (x + 5),
            (y - 2),
            (x + 5 + (sliderValue * (width * 2 - 8).toFloat()).toInt()),
            (y + height + 2),
            colorSliderBox
        )
        context.drawText(
            mc.textRenderer,
            settingInt.displayName + ":" + settingInt.value,
            (x + width / 2),
            (y + (height - 8) / 2),
            Color.WHITE.rgb,
            false
        )
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        if (!settingInt.isVisible()) return
        super.mouseClicked(mouseX, mouseY, mouseButton)
        sliderValue = (mouseX - (x + 4)).toFloat() / (width * 2 - 8).toFloat()
        sliderValue = MathHelper.clamp(sliderValue, 0.0f, 1.0f)
        settingInt.value = denormalizeValue(settingInt, sliderValue).toInt()
    }
}
