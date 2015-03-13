\version "2.18.2"
\include "articulate.ly"
\header {
  title = "Song"
  subtitle = "(tune)"
  composer = "Me"
  meter = "Allegro"
  piece = "Theme 3"
  tagline = \markup {
    \column {
      "Game theme 3 modified from"
      "LilyPond example file by Amelie Zapf,"
      "Berlin 07/07/2003"
    }
  }
}

%#(set-global-staff-size 16)

%%%%%%%%%%%% Keys'n'thangs %%%%%%%%%%%%%%%%%

global = { \time 4/4 }

Key = { \key c \minor }

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

% ############ Rhythm Section #############

%% ------ Piano ------
rhUpper = \relative c'' {
  \voiceOne
  \Key
  e16 [c a f'] d [b gis e'] e [c a f'] d [b gis e'] |  % bar 1
  e [c a a'] f [d b g'] e [c a f'] b, [gis e e']    |  % bar 2
  c'16 [a f c] bes [e g bes] a [f c a] g [bes e g]  |  % bar 3
  f [b d gis] a [e c b] e [c a f] e [gis b e]       |  % bar 4
  
}
rhLower = \relative c' {
  \voiceTwo
  \Key
  r1 | r | r
}

lhUpper = \relative c' {
  \voiceOne
  \Key
  r1 | r | r
}
lhLower = \relative c {
  \voiceTwo
  \Key
  a,16 r r a gis r r gis a r r c gis r r b |  % bar 1
  c r r c d r r d c r r a gis r r b        |  % bar 2
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
  hh4 <hh sn> hh <hh sn> |
  hh4 <hh sn> hh <hh sn> |
  hh4 <hh sn> hh <hh sn> |
}
down = \drummode {
  \voiceTwo
  bd4 s bd s
  bd4 s bd s
  bd4 s bd s
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
  \unfoldRepeats \articulate <<
    <<
      \new StaffGroup = "horns" <<
	\new Staff = "trumpet" \trumpet
	\new Staff = "altosax" \altoSax
      >>
      \new StaffGroup = "rhythm" <<
	\new PianoStaff = "piano" \piano
	\new DrumStaff \drumContents
      >>
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
      tempoWholesPerMinute = #(ly:make-moment 144 4)
    }
  }
}
