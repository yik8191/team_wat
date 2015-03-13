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

% ############ Rhythm Section #############

%% ------ Piano ------
rhUpper = \relative c'' {
  \voiceOne
  \Key
  b8 b b d d d  e4 d c   |  % bar 1
  b8 b b d8 d d a4 g fis |  % bar 2
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
  \unfoldRepeats \articulate
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
  
  \layout {
    \context { \Staff \RemoveEmptyStaves }
    \context {
      \Score
    }
  }
  \midi {
    \context {
      \Score
      tempoWholesPerMinute = #(ly:make-moment 120 4)
    }
  }
}
