package no.advide

import java.awt.Color

class FormattedLine {
  String text
  Map<Integer, FormatChange> changes = [:]
  boolean hasSeparatorLine

  FormattedLine() {
    changes[0] = new FormatChange(index: 0, changeColor: Color.black)
  }

  void setColor(color) {
    changeAt(0).changeColor = color
  }

  Color getColor() {
    changeAt(0).changeColor
  }

  void formatSubstring(int startIndex, Color color) {
    changeAt(startIndex).changeColor = color
  }

  void formatSubstring(int startIndex, int length, Color color) {
    changeAt(startIndex).changeColor = color
    changeAt(startIndex + length).revertColorChange = true
  }

  void highlightSubstring(int startIndex, int length, Color color) {
    changeAt(startIndex).highlight = [length: length, color: color]
  }

  private FormatChange changeAt(int startIndex) {
    if (!changes[startIndex]) {
      changes[startIndex] = new FormatChange(index: startIndex)
    }
    changes[startIndex]
  }
}
