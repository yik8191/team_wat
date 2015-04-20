\version "2.18.2"
\include "articulate.ly"
\header {
  title = "Song"
  subtitle = "(tune)"
  composer = "Me"
  meter = "Allegretto"
  piece = "Theme 1"
  tagline = \markup {
    \column {
      "Game Music theme 1"
      "LilyPond example file by Amelie Zapf,"
      "Berlin 07/07/2003"
    }
  }
}

%#(set-global-staff-size 16)

%%%%%%%%%%%% Keys'n'thangs %%%%%%%%%%%%%%%%%

global = { \time 12/8 }

Key = { \key g \major }

% ############ Horns ############

% ------ Trumpet ------
trpt = \transpose c d \relative c'' {
  \Key
}
trumpet = {
  \global
  \set Staff.instrumentName = #"Trumpet"
  \clef treble
  <<
    \trpt
  >>
}

% ------ Alto Saxophone ------
alto = \transpose c a \relative c' {
  \Key
}
altoSax = {
  \global
  \set Staff.instrumentName = #"Alto Sax"
  \clef treble
  <<
    \alto
  >>
}

% ############ Strings ####################
cello = \relative c {
  \Key
  g2.~ g4.~ g8 a8 b8 | % bar1
  g2.~ g4.~ g8 a8 b8 | % bar2
  g2.  c2.           | % bar3
  b1.                | % bar4
  e,2.~ e4.~ e8 g8 fis8 | % bar5
  e2.~ e4.~ e8 fis8 g8 | % bar6
  a2. e4.~ e8 fis8 g8 | % bar7
  fis1.               | % bar8
  <e b'>1.                | % bar9
  <e b'>1.                | % bar10
  g2.     fis2.         | % bar11
  e2.     d2.         | % bar12
  a'1.                | % bar13
  e'1.    |
  d2. a2. |
  e4. fis4. g2.
}
violonCello = {
  \global
  \set Staff.midiInstrument = #"cello"
  \set Staff.instrumentName = #"Cello "
  \clef bass
  <<
    \cello
  >>
}



% ############ Rhythm Section #############

%% ------ Piano ------
rhUpper = \relative c'' {
  \voiceOne
  \Key
  b8 b b d d d  e4 d c   |  % bar 1
  b8 b b d8 d d a4 g fis |  % bar 2
  b8 b d d d b e e g g fis e | % bar 3
  e8 d c b c d a4 fis dis| % bar 4
  e8 fis g a b c b4 e,4 fis4 | % bar 5
  e8 fis g a b c b2. | % bar 6
  e8 d c b c d c4 b4 a4 | % bar 7
  g8 fis e fis g fis dis4.~ dis4 dis8 | % bar 8
  e8 fis e g fis e a b c b g fis | % bar 9
  e8 fis e g fis e a e' d c b c | % bar 10
  b8 g' fis a fis c a fis' e fis b, a | % bar 11
  g8 e' d e c a fis d' c b g e  | % bar 12
  c8 e a c b a c, e a c a fis | % bar 13
  b,8 e g b g fis b g fis b g e | % bar 14
  a8 g fis e fis g d c b a b c | % bar 15
  b8 e d c b a g b d g a c | % bar 16
}
rhLower = \relative c' {
  \voiceTwo
  \Key

}

lhUpper = \relative c' {
  \voiceOne
  \Key

}
lhLower = \relative c {
  \voiceTwo
  \Key
}

PianoRH = {
  \clef treble
  \global
  \set Staff.midiInstrument = #"acoustic grand"
  <<
    \new Voice = "one" \rhUpper
    \new Voice = "two" \rhLower
  >>
}
PianoLH = {
  \clef bass
  \global
  \set Staff.midiInstrument = #"acoustic grand"
  <<
    \new Voice = "one" \lhUpper
    \new Voice = "two" \lhLower
  >>
}

piano = {
  <<
    \set PianoStaff.instrumentName = #"Piano"
    \new Staff = "upper" \PianoRH
    \new Staff = "lower" \PianoLH
  >>
}
% ------ Drums ------
up = \drummode {
  \voiceOne
}
down = \drummode {
  \voiceTwo
}

drumContents = {
  \global
  <<
    \set DrumStaff.instrumentName = #"Drums"
    \new DrumVoice \up
    \new DrumVoice \down
  >>
}

%%%%%%%%% It All Goes Together Here %%%%%%%%%%%%%%%%%%%%%

\score {
  \unfoldRepeats \articulate
    <<
      \new StaffGroup = "horns" <<
	\new Staff = "trumpet" \trumpet
	\new Staff = "altosax" \altoSax
      >>
      \new StaffGroup = "strings" <<
	\new Staff = "cello" \violonCello
      >>
      \new StaffGroup = "rhythm" <<
	\new PianoStaff = "piano" \piano
	\new DrumStaff \drumContents
      >>
    >>
  
  \layout {
    \context { \Staff \RemoveEmptyStaves }
    \context {
      \Score
    }
  }
  \midi {
    \context {
      \Score
      tempoWholesPerMinute = #(ly:make-moment 180 4)
    }
  }
}
