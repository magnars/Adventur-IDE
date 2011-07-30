package no.advide.commands

import java.awt.Color
import no.advide.FormattedLine
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.text.WordUtils

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

  // updateDocument vil nå fungere slik:
  //   - ber dokumentet slå sammen alle gamle linjene
  //   - ber dokumentet splitte opp linjen etter nye indekser
  //   - Document har ansvar for å holde orden på cursoren

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
