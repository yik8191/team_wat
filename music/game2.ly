\version "2.18.2"
\include "articulate.ly"
\header {
  title = "Game Design"
  subtitle = "32 bars"
  composer = "Austin Liu"
  meter = "Allegretto"
  piece = "Theme 2"
  tagline = \markup {
    \column {
      "Based off of the"
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
  g8 c, g' b d  c aes c  |                % bar 5
  g c, g' b d  c aes c  |                 % bar 6
  b g b d g aes g g,     |                % bar 7
  b' f aes  d, f b, d g,  |               % bar 8
  f g f aes g aes g c |                   % bar 9
  f, g f aes g aes g c |                  % bar 10
  bes g bes f aes g aes ees |             % bar 11
  f ees d g f aes g f |                    % bar 12
  ees c d c ees c b f' |                   % bar 13
  ees c d c ees c g' c, |                 % bar 14
  aes' f c' aes g ees d f |              % bar 15
  ees d c c b g b d |                   % bar 16
}
rhLower = \relative c' {
  \voiceTwo
  \Key
  % g8 c, g' c, g' c, aes' c,  |            % bar 1
  % g' c, g' c, g' c, aes' c,  |            % bar 2
  % aes' c, g' c, f c ees d |               % bar 3
  % aes' c, g' c, f c ees d |               % bar 4
  % g8 c, g' b d  c aes c  |                % bar 5
  % g c, g' b d  c aes c  |                 % bar 6
  % b g b d g aes g g,     |                % bar 7
  % b' f aes  d, f b, d g,  |               % bar 8
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
  d8 r r r aes r g r  |              % bar 3
  d'8 r r r aes r g r  |              % bar 4
  f8 r r r r r aes g  |              % bar 5
  f8 r r r r r aes g  |              % bar 6
  f8 r r r b r d r  |              % bar 7
  aes8 r f r aes r g r |             % bar 8 
  aes8 r r f ees r r g  |            % bar 9
  aes8 r r f ees r r g  |            % bar 10
  g8 r r ees c r r aes' |              % bar 11
  aes8 r f r aes r d, r  |          % bar 12
  ees8 r d r c r f r |               % bar 13
  ees8 r d r c r ees r |               % bar 14
  f8 r aes r ees r f r  |            % bar 15
  g8 r ees r d r g r  |                % bar 16

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
  hh4 hh hh hh8 hh |  % bar 1
  hh4 hh hh hh8 hh |
  hh4 hh hh hh8 hh |
  hh4 hh hh hh8 hh |
  hh4 hh hh hh8 hh |  % bar 5
  hh4 hh hh hh8 hh |
  hh4 hh hh hh8 hh |
  hh4 hh hh hh8 hh |
  hh r4 hh8 hh r4 hh8  |  % bar 9
  hh r4 hh8 hh r4 hh8  |  % bar 10
  hh r4 hh8 hh r4 hh8  |  % bar 11
  hh r4 hh8 hh r4 hh8 |  % bar 12
  hh4 hh hh hh |
  hh4 hh hh hh |
  hh4 hh hh hh |
  hh4 hh hh hh |
}
down = \drummode {
  \voiceTwo
  bd4 sn bd sn
  bd4 sn bd sn
  bd4 sn bd sn
  bd4 sn bd sn
  bd4 sn bd sn
  bd4 sn bd sn
  bd4 sn bd sn
  bd4 sn bd sn
  
  bd4 sn bd sn
  bd4 sn bd sn
  bd4 sn bd sn
  bd4 sn bd sn
  bd4 sn bd sn
  bd4 sn bd sn
  bd4 sn bd sn
  bd4 sn bd sn
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
	\new Staff = "trumpet" \repeat volta 5 \trumpet
	\new Staff = "altosax" \repeat volta 5 \altoSax
      >>
      \new StaffGroup = "rhythm" <<
	\new PianoStaff = "piano" \repeat volta 5 \piano
	\new DrumStaff \repeat volta 5 \drumContents
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
