package no.advide.commands

class ContinueCommand extends Command {
  ContinueCommand(fragment) {
    super(fragment)
  }

  static boolean matches(List<String> strings, int i) {
    strings[i] in ["!!!", "-- fortsett --"]
  }

  static int numMatchingLines(List<String> strings, int i) {
    1
  }
}
