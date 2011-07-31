package no.advide.commands

import java.awt.Color
import no.advide.DocumentFragment
import no.advide.FormattedLine

class ProseCommand extends Command {

  DocumentFragment fragment

  static def matches(List<String> strings, int fromIndex) {
    strings[fromIndex] =~ /^[a-zA-ZæøåÆØÅ]/
  }

  static Object numMatchingLines(List<String> strings, int fromIndex) {
    int index = fromIndex
    while (index < strings.size() && matches(strings, index)) index++
    index - fromIndex
  }

  ProseCommand(DocumentFragment fragment) {
    this.fragment = fragment
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    fragment.lines.collect { new FormattedLine(text: it, color: Color.black)}
  }

}
