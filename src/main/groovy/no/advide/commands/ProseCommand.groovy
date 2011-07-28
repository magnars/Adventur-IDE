package no.advide.commands

import no.advide.FormattedLine
import org.apache.commons.lang3.text.WordUtils
import java.awt.Color

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
  List<FormattedLine> getFormattedLines() {
    wrappedLines().collect {l -> new FormattedLine(text: l, color: Color.black)}
  }

  @Override
  List<String> toNewScript() {
    wrappedLines()
  }

  private String[] wrappedLines() {
    WordUtils.wrap(oneLongString(), width).split("\n")
  }

  private String oneLongString() {
    input.join(" ")
  }

  void setWidth(int width) {
    this.width = width
  }

}
