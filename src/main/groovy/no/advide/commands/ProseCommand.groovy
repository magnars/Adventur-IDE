package no.advide.commands

import no.advide.FormattedLine
import org.apache.commons.lang3.text.WordUtils
import java.awt.Color
import no.advide.Cursor
import org.apache.commons.lang3.StringUtils

class ProseCommand extends Command {

  def input
  def width = 80

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
  }

  @Override
  void setCursor(Cursor cursor, Object localCursorY) {
    super.setCursor(cursor, localCursorY)
    updateCursor()
  }

  void updateCursor() {
    Cursor c = findCursorPositionInRewrappedLines()
    cursor.x = c.x
    cursor.y = cursor.y - localCursorY + c.y
    localCursorY = c.y
  }

  private Cursor findCursorPositionInRewrappedLines() {
    Cursor c = createCursorAsIfOnOneLongString()
    def lines = wrappedLines()
    while (cursorNotOnCurrentLine(c, lines)) {
      moveCursorToNextLine(c, lines)
    }
    return c
  }

  private Cursor createCursorAsIfOnOneLongString() {
    return new Cursor(x: totalLengthOfPreceedingLines() + cursor.x, y: 0)
  }

  private boolean cursorNotOnCurrentLine(Cursor c, String[] lines) {
    return c.x > lines[c.y].size()
  }

  private def moveCursorToNextLine(Cursor c, String[] lines) {
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
    wrappedLines().collect {l -> new FormattedLine(text: l, color: Color.black)}
  }

  @Override
  List<String> toNewScript() {
    wrappedLines()
  }

  private String[] wrappedLines() {
    StringUtils.splitPreserveAllTokens(WordUtils.wrap(oneLongString(), width), '\n')
  }

  private String oneLongString() {
    input.join(" ")
  }

  void setWidth(int width) {
    this.width = width
  }

}
