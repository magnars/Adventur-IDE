package no.advide.commands

import no.advide.FormattedLine
import org.apache.commons.lang3.text.WordUtils
import java.awt.Color
import no.advide.Cursor
import org.apache.commons.lang3.StringUtils

class ProseCommand extends Command {

  def input
  def width = 80
  def lines

  static def matches(List<String> strings, int fromIndex) {
    strings[fromIndex] =~ /^[a-zA-ZæøåÆØÅ]/
  }

  static Object numMatchingLines(List<String> strings, int fromIndex) {
    int index = fromIndex
    while (index < strings.size() && matches(strings, index)) index++
    index - fromIndex
  }

  ProseCommand(List<String> strings) {
    input = strings
    lines = rewrapInput()
  }

  void setWidth(int width) {
    this.width = width
    lines = rewrapInput()
  }

  @Override
  void setCursor(Cursor cursor, Object localCursorY) {
    super.setCursor(cursor, localCursorY)
    if (cursor.x == 0) { ensureCursorRemainsAtStartOfLine() }
    updateCursorPosition()
  }

  void ensureCursorRemainsAtStartOfLine() {
    Cursor c = findCursorPositionInLines()
    if (c.x != 0) {
      splitLinesAtCursorPosition(c)
    }
  }

  private def splitLinesAtCursorPosition(Cursor c) {
    def current = lines[c.y]
    def pre = current.substring(0, c.x - 1)
    def post = current.substring(c.x)
    def split = [pre, post]
    lines.remove(c.y)
    lines.addAll(c.y, split)
  }

  void updateCursorPosition() {
    Cursor c = findCursorPositionInLines()
    cursor.x = c.x
    cursor.y = cursor.y - localCursorY + c.y
  }

  private Cursor findCursorPositionInLines() {
    Cursor c = createCursorAsIfOnOneLongString()
    while (cursorNotOnCurrentLine(c)) {
      moveCursorToNextLine(c)
    }
    return c
  }

  private Cursor createCursorAsIfOnOneLongString() {
    return new Cursor(x: totalLengthOfPreceedingLines() + cursor.x, y: 0)
  }

  private boolean cursorNotOnCurrentLine(Cursor c) {
    return c.x > lines[c.y].size()
  }

  private def moveCursorToNextLine(Cursor c) {
    c.x -= lines[c.y].size() + 1 // also count space after line
    c.y += 1
  }

  int totalLengthOfPreceedingLines() {
    if (localCursorY == 0) return 0
    def numSpacesBetweenLines = localCursorY
    def preceedingLines = input[0..(localCursorY - 1)]
    preceedingLines.sum { it.size() } + numSpacesBetweenLines
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    lines.collect { new FormattedLine(text: it, color: Color.black)}
  }

  @Override
  List<String> toNewScript() {
    lines
  }

  private List<String> rewrapInput() {
    StringUtils.splitPreserveAllTokens(WordUtils.wrap(input.join(" "), width), '\n')
  }

}
