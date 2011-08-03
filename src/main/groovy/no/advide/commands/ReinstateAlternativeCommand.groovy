package no.advide.commands

import no.advide.DocumentFragment
import no.advide.RoomNumber

class ReinstateAlternativeCommand extends Command {

  static def matches(DocumentFragment fragment) {
    matchesOldForm(fragment) || matchesNewForm(fragment)
  }

  private static def matchesNewForm(DocumentFragment fragment) {
    fragment.lines.first() =~ /^- #\d+$/
  }

  private static def matchesOldForm(DocumentFragment fragment) {
    fragment.lines.first() =~ /^\*\d+$/
  }

  static int numMatchingLines(DocumentFragment fragment) {
    1
  }

  ReinstateAlternativeCommand(DocumentFragment fragment) {
    super(fragment)
  }

  @Override
  List<String> toNewStyle() {
    return [ "- #${number}" ]
  }

  @Override
  List<String> toOldStyle() {
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
    return matchesOldForm(fragment) ? 1 : 3
  }

}
