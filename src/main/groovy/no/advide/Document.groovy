package no.advide

import antlr.StringUtils

class Document {
  List<String> lines
  Cursor cursor

  Document(List<String> lines, coords) {
    this.lines = lines
    this.cursor = new Cursor(lines, coords.x, coords.y, coords.scrollTop)
  }

  void setCursor(LinkedHashMap coords) {
    this.cursor._x = coords.x
    this.cursor._y = coords.y
  }

  void removeCharBefore(Integer x, Integer y) {
    cursor.anchor()
    if (cursor.y == y && cursor.x >= x) cursor.left()

    def pre = lines[y].substring(0, x - 1)
    def post = lines[y].substring(x)
    lines[y] = pre + post
  }

  void mergeLineWithPrevious(Integer y) {
    cursor.anchor()
    if (cursor.y == y) cursor._x += lines[y - 1].size()
    if (cursor.y >= y) cursor.up()

    fragmentsAt(y).each { it.length -= 1 }
    fragmentsAfter(y).each { it.offset.y -= 1 }

    lines[y - 1] += lines[y]
    lines.remove(y)
  }

  void splitAt(Integer x, Integer y) {
    def pre = lines[y].substring(0, x)
    def post = lines[y].substring(x)
    def split = [pre, post]

    cursor.anchor()
    if (cursor.y == y && cursor.x >= x) { cursor._y += 1; cursor._x -= pre.size() }
    else if (cursor.y > y) cursor.down()

    fragmentsAt(y).each { it.length += 1 }
    fragmentsAfter(y).each { it.offset.y += 1 }

    lines.remove(y)
    lines.addAll(y, split)
  }

  void removeLine(Integer y) {
    cursor.anchor()
    if (cursor.y == y) cursor.allLeft()
    if (cursor.y > y) cursor.up()

    lines.remove(y)

    fragmentsAt(y).each { it.length -= 1 }
    fragmentsAfter(y).each { it.offset.y -= 1 }
  }

  void replaceLine(Integer y, String string) {
    lines[y] = string
    cursor.anchor()
    if (cursor.y == y && cursor.x > 0) cursor.allRight()
  }

  void addLineAfter(Integer y, String s) {
    cursor.anchor()
    if (cursor.y > y) cursor.down()

    lines.add(y + 1, s)

    fragmentsAt(y).each { it.length += 1 }
    fragmentsAfter(y).each { it.offset.y += 1 }
  }

  void insertAt(Integer x, Integer y, s) {
    cursor.anchor()
    if (cursor.y == y && cursor.x >= x) cursor._x += s.size()
    def pre = lines[y].substring(0, x)
    def post = lines[y].substring(x)
    lines[y] = pre + s + post
  }

  void stripTrailingSpaces() {
    lines.eachWithIndex { line, i ->
      lines[i] = StringUtils.stripBack(line, " ")
    }
  }

  List<DocumentFragment> fragments = []

  DocumentFragment createFragment(offset, length) {
    def f = new DocumentFragment(offset, length, this)
    fragments << f
    return f
  }

  private List<DocumentFragment> fragmentsAt(int y) {
    fragments.findAll { it.inside(y) }
  }

  private List<DocumentFragment> fragmentsAfter(int y) {
    fragments.findAll { it.offset.y > y }
  }

}
