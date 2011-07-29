package no.advide

import java.awt.Color

class FormattedLine {
  String text
  Map<Integer, FormatChange> changes = [:]

  FormattedLine() {
    changes[0] = new FormatChange(index: 0, changeColor: DEFAULT_COLOR)
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

  private FormatChange changeAt(int startIndex) {
    if (!changes[startIndex]) {
      changes[startIndex] = new FormatChange(index: startIndex)
    }
    changes[startIndex]
  }

  static Color DEFAULT_COLOR = new Color(50, 50, 200)
}
