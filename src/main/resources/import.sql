--Franchises
INSERT INTO franchise (description, name) VALUES ('Star Wars is an American epic space opera multimedia franchise created by George Lucas.', 'Star Wars');
INSERT INTO franchise (description, name) VALUES ('The Marvel Cinematic Universe (MCU) is an American media franchise and shared universe centered on a series of superhero films produced by Marvel Studios. The films are based on characters that appear in American comic books published by Marvel Comics.', 'Marvel Cinematic Universe');
INSERT INTO franchise (description, name) VALUES ('The Lord of the Rings is the saga of a group of sometimes reluctant heroes who set forth to save their world from consummate evil.', 'The Lord of the Rings');
INSERT INTO franchise (description, name) VALUES ('The Hunger Games is an annual event in which one boy and one girl aged 12–18 from each of the twelve districts surrounding the Capitol are selected by lottery to compete in a televised battle royale to the death.', 'Hunger Games');
INSERT INTO franchise (description, name) VALUES ('The Matrix describes a future in which reality perceived by humans is actually the Matrix, a simulated reality created by sentient Machines in order to pacify and subdue the human population while their bodies heat and electrical activity are used as an energy source.', 'The Matrix');
--Movies
INSERT INTO movie (director, genre, picture_url, release_year, title, trailer_url, franchise_id) VALUES ('George Lucas', 'Sci-fi', 'C:\Pictures\Episode_I_–_The_Phantom_Menace.png', '1999', 'Episode I – The Phantom Menace', 'https://www.youtube.com/watch?v=uMoSnrd7i5c', 1);
INSERT INTO movie (director, genre, picture_url, release_year, title, trailer_url, franchise_id) VALUES ('George Lucas', 'Sci-fi', 'C:\Pictures\Episode_II_–_Attack_of_the_Clones.png', '2002', 'Episode II – Attack of the Clones', 'https://www.youtube.com/watch?v=1Q5Pp7EW4Wg', 1);
INSERT INTO movie (director, genre, picture_url, release_year, title, trailer_url, franchise_id) VALUES ('Jon Favreau', 'Sci-fi, Action', 'C:\Pictures\Iron_man.png', '2008', 'Iron Man', 'https://www.imdb.com/video/vi447873305?playlistId=tt0371746&ref_=tt_ov_vi', 2);
INSERT INTO movie (director, genre, picture_url, release_year, title, trailer_url, franchise_id) VALUES ('Joe Johnston', 'Sci-fi, Action', 'C:\Pictures\Captain_America_The_First_Avenger.png', '2011', 'Captain America: The First Avenger', 'https://www.imdb.com/video/vi2912787481?playlistId=tt0458339&ref_=tt_ov_vi', 2);
INSERT INTO movie (director, genre, picture_url, release_year, title, trailer_url, franchise_id) VALUES ('Peter Jackson', 'Sci-fi, Action', 'C:\Pictures\The_Lord_of_the_Rings_The_Fellowship_of_the_Ring.png', '2001', 'The Lord of the Rings: The Fellowship of the Ring', 'https://www.imdb.com/video/vi2073101337?playlistId=tt0120737&ref_=tt_ov_vi', 3);
--Characters
INSERT INTO "character" (alias, full_name, gender, picture_url) VALUES ('Darth Vader', 'Anakin Skywalker', 'Male', 'C:\\Pictures\\Anakin_Skywalker.png');
INSERT INTO "character" (alias, full_name, gender, picture_url) VALUES ('', 'Obiwan Kenobi', 'Male', 'C:\\Pictures\\Obiwan_Kenobi.png');
INSERT INTO "character" (alias, full_name, gender, picture_url) VALUES ('Iron Man', 'Tony Stark', 'Male', 'C:\\Pictures\\Tony_Stark.png');
INSERT INTO "character" (alias, full_name, gender, picture_url) VALUES ('Captain America', 'Steve Rogers', 'Male', 'C:\\Pictures\\Steve_Rogers.png');
INSERT INTO "character" (alias, full_name, gender, picture_url) VALUES ('', 'Frodo Baggins', 'Male', 'C:\\Pictures\\Frodo_Baggins.png');
INSERT INTO "character" (alias, full_name, gender, picture_url) VALUES ('Mithrandir', 'Gandalf', 'Male', 'C:\\Pictures\\Gandalf.png');
--CharacterMovie
INSERT INTO character_movie (character_id, movie_id) VALUES (1, 1);
INSERT INTO character_movie (character_id, movie_id) VALUES (1, 2);
INSERT INTO character_movie (character_id, movie_id) VALUES (2, 1);
INSERT INTO character_movie (character_id, movie_id) VALUES (2, 2);
INSERT INTO character_movie (character_id, movie_id) VALUES (3, 3);
INSERT INTO character_movie (character_id, movie_id) VALUES (4, 4);
INSERT INTO character_movie (character_id, movie_id) VALUES (5, 5);
INSERT INTO character_movie (character_id, movie_id) VALUES (6, 5);