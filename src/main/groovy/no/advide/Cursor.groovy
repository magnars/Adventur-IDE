package no.advide

class Cursor {
  int _x
  int _y
  private List<String> lines

  Cursor(List<String> lines, x, y) {
    this.lines = lines
    _x = x
    _y = y
  }

  boolean at(LinkedHashMap coords) {
    coords.x == x && coords.y == y
  }

  boolean atLine(y) {
    getY() == y
  }

  int getX() {
    Math.min(_x, maxX)
  }

  int getY() {
    _y = Math.min(_y, maxY)
    _y
  }

  void anchor() {
    _x = getX()
    _y = getY()
  }

  void right() {
    if (!atEndOfLine()) {
      _x = x + 1
    } else if (!atLastLine()) {
      _y = y + 1
      _x = 0
    }
  }

  void left() {
    if (!atStartOfLine()) {
      _x = x - 1
    } else if (!atFirstLine()) {
      _y = y - 1
      _x = maxX
    }
  }

  void down() {
    if (!atLastLine()) {
      _y = y + 1
    } else if (!atEndOfLine()) {
      _x = maxX
    }
  }

  void up() {
    if (!atFirstLine()) {
      _y = y - 1
    } else if (!atStartOfLine()) {
      _x = 0
    }
  }

  void allRight() {
    _x = maxX
  }

  void allLeft() {
    _x = 0
  }

  void allDown() {
    _y = maxY
  }

  void allUp() {
    _y = 0
  }

  private boolean atFirstLine() {
     getY() == 0
  }

  private boolean atStartOfLine() {
    getX() == 0
  }

  private boolean atLastLine() {
    getY() == getMaxY()
  }

  private boolean atEndOfLine() {
    getX() == getMaxX()
  }

  private int getMaxX() {
    return width
  }

  private int getMaxY() {
    return height - 1
  }

  private int getHeight() {
    return lines.size()
  }

  private int getWidth() {
    return currentLine.size()
  }

  private String getCurrentLine() {
    return lines[y]
  }

  @Override
  String toString() {
    return "[x:$x,y:$y]"
  }

  @Override
  boolean equals(Object obj) {
    obj.x == x && obj.y == y
  }


}
