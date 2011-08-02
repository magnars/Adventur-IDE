package no.advide.commands

import java.awt.Color

class ProseCommand extends Command {

  static def matches(List<String> strings, int fromIndex) {
    strings[fromIndex] =~ /^[a-zA-ZæøåÆØÅ]/
  }

  static int numMatchingLines(List<String> strings, int fromIndex) {
    int index = fromIndex
    while (index < strings.size() && matches(strings, index)) index++
    index - fromIndex
  }

  ProseCommand(fragment) {
    super(fragment)
  }

  @Override
  Color getColor() {
    Color.black
  }


}
