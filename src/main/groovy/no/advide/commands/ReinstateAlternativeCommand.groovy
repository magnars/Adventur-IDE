package no.advide.commands

import no.advide.DocumentFragment
import no.advide.RoomNumber

class ReinstateAlternativeCommand extends Command {
  ReinstateAlternativeCommand(DocumentFragment fragment) {
    super(fragment)
  }

  static def matches(List<String> strings, int i) {
    matchesOldForm(strings, i) || matchesNewForm(strings, i)
  }

  private static def matchesNewForm(List<String> strings, int i) {
    strings[i] =~ /^- #\d+$/
  }

  private static def matchesOldForm(List<String> strings, int i) {
    strings[i] =~ /^\*\d+$/
  }

  static int numMatchingLines(Object lines, Object fromIndex) {
    1
  }

  @Override
  List<String> toNewScript() {
    return [ "- #${number}" ]
  }

  @Override
  List<String> toOldScript() {
    return [ "*${number}" ]
  }

  @Override
  List<RoomNumber> getRoomNumbers() {
    return [ new RoomNumber(
        number: number,
        position: fragment.translate([x:numberIndex, y:0])
    ) ]
  }

  private int getNumber() {
    return fragment.lines.first().substring(numberIndex).toInteger()
  }

  private int getNumberIndex() {
    return matchesOldForm(fragment.lines, 0) ? 1 : 3
  }

}
