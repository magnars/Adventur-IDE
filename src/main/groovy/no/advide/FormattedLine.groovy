package no.advide

import java.awt.Color
import no.advide.ui.Theme

class FormattedLine {
  String text
  Map<Integer, FormatChange> changes = [:]
  boolean hasSeparatorLine
  boolean isEmbossedTop
  boolean isEmbossed
  boolean isEmbossedBottom
  String icon
  String prefix
  int prefixPosition = 0
  Color prefixColor = Theme.prefix

  FormattedLine() {
    changes[0] = new FormatChange(index: 0, changeColor: Color.pink)
  }

  void setColor(color) {
    changeAt(0).changeColor = color
  }

  Color getColor() {
    changeAt(0).changeColor
  }

  void formatSubstring(int startIndex, int length, Color color) {
    changeAt(startIndex).changeColor = color
    changeAt(startIndex + length).revertColorChange = true
  }

  void highlightSubstring(int startIndex, int length, Color color) {
    changeAt(startIndex).highlight = [length: length, color: color]
  }

  void cementPrefix() {
    if (prefix) changeAt(prefixPosition).prefix = [text: prefix, color: prefixColor]
  }

  FormatChange changeAt(int startIndex) {
    if (!changes[startIndex]) {
      changes[startIndex] = new FormatChange(index: startIndex)
    }
    changes[startIndex]
  }
}
