package views.html.site

import play.api.i18n.Lang

import lila.app.templating.Environment.{ *, given }
import lila.app.ui.ScalatagsTemplate.{ *, given }

object help:

  private def header(text: Frag)          = tr(th(colspan := 2)(p(text)))
  private def row(keys: Frag, desc: Frag) = tr(td(cls := "keys")(keys), td(cls := "desc")(desc))
  private val or                          = tag("or")("/")
  private val kbd                         = tag("kbd")
  private def voice(text: String)         = strong(cls := "val-to-word", text)
  private def phonetic(text: String)      = strong(cls := "val-to-word phonetic", text)

  private def navigateMoves(using Lang) = frag(
    header(trans.navigateMoveTree()),
    row(frag(kbd("←"), or, kbd("→")), trans.keyMoveBackwardOrForward()),
    row(frag(kbd("k"), or, kbd("j")), trans.keyMoveBackwardOrForward()),
    row(frag(kbd("↑"), or, kbd("↓")), trans.keyGoToStartOrEnd()),
    row(frag(kbd("0"), or, kbd("$")), trans.keyGoToStartOrEnd()),
    row(frag(kbd("home"), or, kbd("end")), trans.keyGoToStartOrEnd())
  )
  private def flip(using Lang)       = row(kbd("f"), trans.flipBoard())
  private def zen(using Lang)        = row(kbd("z"), trans.preferences.zenMode())
  private def helpDialog(using Lang) = row(kbd("?"), trans.showHelpDialog())
  private def localAnalysis(using Lang) = frag(
    row(kbd("l"), trans.toggleLocalAnalysis()),
    row(kbd("space"), trans.playComputerMove()),
    row(kbd("x"), trans.showThreat())
  )

  def round(using Lang) =
    frag(
      h2(trans.keyboardShortcuts()),
      table(
        tbody(
          navigateMoves,
          header(trans.other()),
          flip,
          zen,
          helpDialog
        )
      )
    )
  def puzzle(using Lang) =
    frag(
      h2(trans.keyboardShortcuts()),
      table(
        tbody(
          navigateMoves,
          header(trans.analysisOptions()),
          localAnalysis,
          row(kbd("n"), trans.puzzle.nextPuzzle()),
          header(trans.other()),
          flip,
          zen,
          helpDialog
        )
      )
    )
  def analyse(isStudy: Boolean)(using Lang) =
    frag(
      h2(trans.keyboardShortcuts()),
      table(
        tbody(
          row(frag(kbd("←"), or, kbd("→")), trans.keyMoveBackwardOrForward()),
          row(frag(kbd("k"), or, kbd("j")), trans.keyMoveBackwardOrForward()),
          row(kbd("shift"), trans.keyCycleSelectedVariation()),
          row(frag(kbd("↑"), or, kbd("↓")), trans.cyclePreviousOrNextVariation()),
          row(frag(kbd("shift"), kbd("←"), or, kbd("shift"), kbd("K")), trans.keyPreviousBranch()),
          row(frag(kbd("shift"), kbd("→"), or, kbd("shift"), kbd("J")), trans.keyNextBranch()),
          row(frag(kbd("↑"), or, kbd("↓")), trans.keyGoToStartOrEnd()),
          row(frag(kbd("0"), or, kbd("$")), trans.keyGoToStartOrEnd()),
          row(frag(kbd("home"), or, kbd("end")), trans.keyGoToStartOrEnd()),
          header(trans.analysisOptions()),
          flip,
          row(frag(kbd("shift"), kbd("I")), trans.inlineNotation()),
          localAnalysis,
          row(kbd("z"), trans.toggleAllAnalysis()),
          row(kbd("a"), trans.bestMoveArrow()),
          row(kbd("v"), trans.toggleVariationArrows()),
          row(kbd("e"), trans.openingEndgameExplorer()),
          row(frag(kbd("shift"), kbd("space")), trans.playFirstOpeningEndgameExplorerMove()),
          row(kbd("r"), trans.keyRequestComputerAnalysis()),
          row(kbd("enter"), trans.keyNextLearnFromYourMistakes()),
          row(kbd("b"), trans.keyNextBlunder()),
          row(kbd("m"), trans.keyNextMistake()),
          row(kbd("i"), trans.keyNextInaccuracy()),
          row(kbd("c"), trans.focusChat()),
          row(frag(kbd("shift"), kbd("C")), trans.keyShowOrHideComments()),
          helpDialog,
          isStudy option frag(
            header(trans.study.studyActions()),
            row(kbd("d"), trans.study.commentThisPosition()),
            row(kbd("g"), trans.study.annotateWithGlyphs()),
            row(kbd("n"), trans.study.nextChapter()),
            row(kbd("p"), trans.study.prevChapter()),
            row(frag((1 to 6).map(kbd(_))), trans.toggleGlyphAnnotations())
          ),
          header(trans.mouseTricks()),
          tr(
            td(cls := "mouse", colspan := 2)(
              ul(
                li(trans.youCanAlsoScrollOverTheBoardToMoveInTheGame()),
                li(trans.scrollOverComputerVariationsToPreviewThem()),
                li(trans.analysisShapesHowTo())
              )
            )
          )
        )
      )
    )

  def analyseVariationArrow(using Lang) =
    div(cls := "help-ephemeral")(
      p(trans.variationArrowsInfo()),
      table(
        tbody(
          row(kbd("v"), trans.toggleVariationArrows()),
          row(frag(kbd("↑"), or, kbd("↓"), or, kbd("shift")), trans.keyCycleSelectedVariation()),
          row(kbd("→"), trans.playSelectedMove()),
          row(frag(kbd("shift"), kbd("←"), or, kbd("shift"), kbd("K")), trans.keyPreviousBranch()),
          row(frag(kbd("shift"), kbd("→"), or, kbd("shift"), kbd("J")), trans.keyNextBranch())
        )
      )
    )

  def keyboardMove(using Lang) =
    import trans.keyboardMove.*
    frag(
      h2(keyboardInputCommands()),
      table(
        tbody(
          header(performAMove()),
          row(kbd("e2e4"), movePieceFromE2ToE4()),
          row(kbd("5254"), movePieceFromE2ToE4()),
          row(kbd("Nc3"), moveKnightToC3()),
          row(kbd("O-O"), kingsideCastle()),
          row(kbd("O-O-O"), queensideCastle()),
          row(kbd("c8=Q"), promoteC8ToQueen()),
          row(kbd("R@b4"), dropARookAtB4()),
          header(otherCommands()),
          row(kbd("/"), trans.focusChat()),
          row(kbd("clock"), readOutClocks()),
          row(kbd("who"), readOutOpponentName()),
          row(kbd("draw"), offerOrAcceptDraw()),
          row(kbd("resign"), trans.resignTheGame()),
          row(kbd("next"), trans.puzzle.nextPuzzle()),
          row(kbd("upv"), trans.puzzle.upVote()),
          row(kbd("downv"), trans.puzzle.downVote()),
          row(frag(kbd("help"), or, kbd("?")), trans.showHelpDialog()),
          header(tips()),
          tr(
            td(cls := "tips", colspan := 2)(
              ul(
                li(
                  ifTheAboveMoveNotationIsUnfamiliar(),
                  a(target := "_blank", href := "https://en.wikipedia.org/wiki/Algebraic_notation_(chess)")(
                    "Algebraic notation"
                  )
                ),
                li(includingAXToIndicateACapture()),
                li(bothTheLetterOAndTheDigitZero()),
                li(ifItIsLegalToCastleBothWays()),
                li(capitalizationOnlyMattersInAmbiguousSituations()),
                li(toPremoveSimplyTypeTheDesiredPremove())
              )
            )
          )
        )
      )
    )

  def voiceCoords(using Lang) =
    frag(
      h2(trans.voiceCommands.voiceCommands()),
      "This space for rent"
    )

  def voiceMove(using Lang) =
    import trans.voiceCommands.*
    frag(
      h2(voiceCommands()),
      table(
        tbody(
          tr(th(p(instructions()))),
          tr(
            td(cls := "tips")(
              ul(
                li(
                  a(target := "_blank", href := "https://youtu.be/Ibfk4TyDZpY")(
                    "Watch the video tutorial"
                  ),
                  "."
                ),
                li(
                  "Use the ",
                  i(dataIcon := licon.Voice),
                  " button to toggle voice recognition, the ",
                  i(dataIcon := licon.InfoCircle),
                  " button to open this help dialog, and the ",
                  i(dataIcon := licon.Gear),
                  " menu to change speech settings."
                ),
                li(
                  "We show arrows for multiple moves when we're not sure. Speak the color or number of a move"
                    + " arrow to select it."
                ),
                li(
                  "If an arrow shows a sweeping radar, that move will be played when the circle is complete. "
                    + "During this time, you may only say \"",
                  voice("yes"),
                  "\" to play the move immediately, \"",
                  voice("no"),
                  "\" to cancel, or speak the color/number of a different arrow. This timer can be adjusted "
                    + "or turned off in settings."
                ),
                li(
                  "Enable ",
                  strong("Push to Talk"),
                  " in noisy surroundings. Hold shift while speaking commands when this is on."
                ),
                li(
                  "Use the phonetic alphabet to improve recognition of chessboard files. ",
                  phonetics
                ),
                li(
                  a(target := "_blank", href := "/@/schlawg/blog/how-to-lichess-voice/nWrypoWI")(
                    "This blog post"
                  ),
                  " explains the voice move settings in detail."
                )
              )
            )
          )
        )
      ),
      div(cls := "commands")(
          table(
            tbody(
              header(trans.keyboardMove.performAMove()),
              row(voice("e,4"), moveToE4OrSelectE4Piece()),
              row(voice("B"), selectOrCaptureABishop()),
              row(voice("N,c,3"), trans.keyboardMove.moveKnightToC3()),
              row(voice("Q,x,R"), takeRookWithQueen()),
              row(voice("c,8,=,Q"), trans.keyboardMove.promoteC8ToQueen()),
              row(voice("castle"), castle()),
              row(voice("O-O-O"), trans.keyboardMove.queensideCastle()),
              row(phonetic("a,7,g,1"), phoneticAlphabetIsBest()),
              row(voice("draw"), trans.keyboardMove.offerOrAcceptDraw()),
              row(voice("resign"), trans.resignTheGame()),
              row(voice("takeback"), trans.proposeATakeback())
            )
          ),
          table(
            tbody(
              header(trans.keyboardMove.otherCommands()),
              row(voice("no"), cancelTimerOrDenyARequest()),
              row(voice("yes"), playPreferredMoveOrConfirmSomething()),
              row(voice("stop"), sleep()),
              row(voice("mic-off"), turnOffVoiceRecognition()),
              row(voice("next"), trans.puzzle.nextPuzzle()),
              row(voice("upvote"), trans.puzzle.upVote()),
              row(voice("solve"), showPuzzleSolution()),
              row(voice("help"), trans.showHelpDialog()),
              tr(
                td,
                td(button(cls := "button", cls := "all-phrases-button")(trans.showMeEverything()))
            )
          )
        )
      )
    )

  private def phonetics = "abcdefgh"
    .map(_.toString)
    .map: letter =>
      frag(s"${letter.capitalize} is ", phonetic(letter), ". ")
