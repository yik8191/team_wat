\version "2.11.46"
\include "articulate.ly"
\header {
  title = "Song"
  subtitle = "(tune)"
  composer = "Me"
  meter = "moderato"
  piece = "Swing"
  tagline = \markup {
    \column {
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
  g8 c, g' c, g' c, aes' c,  |            % bar 1
  g' c, g' c, g' c, aes' c,  |            % bar 2
  aes' c, g' c, f c ees d |               % bar 3
  aes' c, g' c, f c ees d |               % bar 4
  g8 c, g' b d  c aes c  |               % bar 5
  g c, g' b d  c aes c  |               % bar 6
  b g b d g aes g g,     |               % bar 7
  b' f aes  d, f b, d g,  |               % bar 7 
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
  c8 r r r r r d ees  |              % bar 1
  c8 r r r r r d ees  |              % bar 2
  c8 r r r aes r g r  |              % bar 3
  c8 r r r aes r g r  |              % bar 4
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
  hh4 <hh sn> hh <hh sn> |
  hh4 <hh sn> hh <hh sn> |
  hh4 <hh sn> hh <hh sn> |
  hh4 <hh sn> hh <hh sn> |
  hh4 <hh sn> hh <hh sn> |				  
}
down = \drummode {
  \voiceTwo
  bd4 s bd s
  bd4 s bd s
  bd4 s bd s
  bd4 s bd s
  bd4 s bd s
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
