-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Dec 10, 2019 at 08:22 PM
-- Server version: 5.6.34-log
-- PHP Version: 7.1.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `bartender-data`
--

-- --------------------------------------------------------

--
-- Table structure for table `drink`
--

CREATE TABLE `drink` (
  `id` int(11) NOT NULL,
  `chill_type` int(11) DEFAULT NULL,
  `clipart` varchar(255) DEFAULT NULL,
  `glass_type` int(11) DEFAULT NULL,
  `instructions` varchar(255) DEFAULT NULL,
  `mix_type` int(11) DEFAULT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `drink`
--

INSERT INTO `drink` (`id`, `chill_type`, `clipart`, `glass_type`, `instructions`, `mix_type`, `name`) VALUES
(156, 0, '/img/pint.jpg', 0, '', 2, 'Jack and Coke'),
(158, 1, '/img/coupe.jpg', 1, 'Shake and strain to chilled glass. Garnish with cherry.', 1, 'Corpse Reviver #2'),
(161, 1, '/img/rocks.jpg', 3, 'Muddle sugar cube and bitters. Add rye whiskey and ice. Stir and strain to absinthe-rinsed glass. Garnish with lemon peel. Serve with cup of ice on side.', 1, 'Carondolet Sazerac'),
(163, 0, '/img/pint.jpg', 0, '', 2, 'Tito\'s & Tonic'),
(164, 0, '/img/pint.jpg', 0, '', 2, 'Ketel & Soda'),
(169, 0, '/img/rocks.jpg', 3, 'Muddle bitters, sugar, and lemon peel. Add bourbon and ice, stir.', 2, 'Bevo Old Fashioned'),
(171, 0, '/img/rocks.jpg', 3, 'Muddle bitters, sugar, and lemon peel with a splash of water. Add rye whiskey and ice. Stir.', 2, 'Really Rye Old Fashioned'),
(175, 1, '/img/martini.jpg', 2, 'Stir and strain to chilled glass.', 1, 'River Des Peres Martini'),
(177, 0, '/img/rocks.jpg', 3, 'Fill rocks glass with ice and pour ginger beer, leaving one inch at top of glass. Float a shot of dark rum on top, garnish with lime.', 2, 'Dark and Stormy'),
(178, 1, '/img/martini.jpg', 2, '', 1, 'The Royale'),
(196, 0, '/img/pint.jpg', 5, '', 2, 'Suffering Bastard'),
(200, 0, '/img/pint.jpg', 5, 'Build in a pint glass, add a splash of Coke for color. Silently judge whoever ordered it...', 2, 'Long Island Iced Tea'),
(202, 1, '/img/coupe.jpg', 1, '', 1, 'Midtowner Manhattan'),
(203, 1, '/img/coupe.jpg', 1, 'Stir and strain to chilled glass. Garnish with lemon peel.', 1, 'Rye Manhattan');

-- --------------------------------------------------------

--
-- Table structure for table `drink_recipe`
--

CREATE TABLE `drink_recipe` (
  `drink_id` int(11) NOT NULL,
  `measurement` varchar(255) DEFAULT NULL,
  `ingredient` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `drink_recipe`
--

INSERT INTO `drink_recipe` (`drink_id`, `measurement`, `ingredient`) VALUES
(156, '1.5 oz', '121'),
(156, 'fill', '57'),
(158, '3/4 oz', '46'),
(157, '3/4 oz', '117'),
(157, '3/4 oz', '124'),
(157, '3/4 oz', '116'),
(157, '1 dash', '130'),
(157, '1', '26'),
(158, '3/4 oz', '117'),
(158, '3/4 oz', '124'),
(158, '3/4 oz', '116'),
(158, '1 dash', '130'),
(158, '1', '26'),
(161, '2 oz', '110'),
(161, '3 dashes', '131'),
(161, '1 cube', '35'),
(161, 'rinse', '130'),
(161, '1', '135'),
(163, '1.5 oz', '20'),
(163, 'fill', '118'),
(163, '1', '58'),
(164, '1.5 oz', '92'),
(164, 'fill', '119'),
(164, '1', '58'),
(169, '2 oz', '23'),
(169, '2 dashes', '168'),
(169, '1 cube', '35'),
(169, '1', '135'),
(171, '2 oz', '111'),
(171, '2 dashes', '168'),
(171, '1 cube', '35'),
(171, '1', '135'),
(175, '1.5 oz', '20'),
(175, '0.5 oz', '60'),
(175, 'splash', '173'),
(175, 'heavy splash', '174'),
(175, '1', '61'),
(175, '2', '172'),
(177, '1.5 oz', '99'),
(177, 'fill', '176'),
(177, '1', '165'),
(178, '1.5 oz', '94'),
(178, '.5 oz', '60'),
(178, 'splash', '173'),
(178, '1', '61'),
(196, '2 dashes', '25'),
(196, '1 oz', '190'),
(196, '1 oz', '189'),
(196, '0.5 oz', '115'),
(196, 'fill', '176'),
(196, '1', '188'),
(196, '1', '187'),
(200, '0.5 oz', '191'),
(200, '0.5 oz', '190'),
(200, '0.5 oz', '198'),
(200, '0.5 oz', '199'),
(200, '0.5 oz', '127'),
(200, 'fill', '114'),
(200, 'splash', '57'),
(200, '1', '120'),
(202, '2 oz', '23'),
(202, '.75 oz', '24'),
(202, '1 dash', '25'),
(202, '1', '26'),
(203, '2 oz', '111'),
(203, '0.75 oz', '24'),
(203, '1 dash', '132'),
(203, '1', '135');

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(216);

-- --------------------------------------------------------

--
-- Table structure for table `ingredient`
--

CREATE TABLE `ingredient` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `type` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ingredient`
--

INSERT INTO `ingredient` (`id`, `name`, `type`) VALUES
(24, 'Sweet Vermouth', 6),
(19, 'Absolut', 0),
(20, 'Titos', 0),
(23, 'Buffalo Trace', 4),
(25, 'Angostura Bitters', 8),
(26, 'Cherry', 9),
(35, 'Sugar', 7),
(36, 'Orange Peel', 9),
(46, 'Plymouth', 1),
(47, 'Orange Juice', 7),
(48, 'Grenadine', 7),
(49, 'Sauza Blanco', 3),
(56, 'Captain Morgan', 2),
(57, 'Coke', 7),
(58, 'Lime', 9),
(60, 'Dry Vermouth', 6),
(61, 'Green Olive(s)', 9),
(76, 'Orange Wedge', 9),
(91, 'Stolichnaya', 0),
(92, 'Ketel One', 0),
(93, 'Grey Goose', 0),
(94, 'Tanqueray', 1),
(95, 'Hendrick\'s', 1),
(99, 'Dark Rum', 2),
(100, 'Overproof Rum', 2),
(165, 'Lime Wedge', 9),
(102, 'Silver Tequila', 3),
(103, 'Anejo Tequila', 3),
(104, 'Reposado Tequila', 3),
(105, 'Cazadores Reposado', 3),
(106, 'Cazadores Blanco', 3),
(107, 'Espolon Blanco', 3),
(108, 'Espolon Reposado', 3),
(109, 'Jose Cuervo Gold', 3),
(110, 'Rittenhouse Rye', 4),
(111, 'Bulleit Rye', 4),
(112, 'Bulleit Bourbon', 4),
(113, 'Sprite', 7),
(114, 'Sour Mix', 7),
(115, 'Lime Juice', 7),
(116, 'Lemon Juice', 7),
(117, 'Cointreau', 5),
(118, 'Tonic', 7),
(119, 'Club Soda', 7),
(120, 'Lemon Wedge', 9),
(121, 'Jack Daniels', 4),
(122, 'Jim Beam', 4),
(123, 'Maker\'s Mark', 4),
(124, 'Cocchi Americano', 6),
(125, 'Amaretto', 5),
(126, 'Water', 7),
(127, 'Triple Sec', 5),
(128, 'Absolut Citron', 0),
(129, 'Cranberry Juice', 7),
(130, 'Absinthe', 5),
(131, 'Peychaud\'s Bitters', 8),
(132, 'Orange Bitters', 8),
(135, 'Lemon Peel', 9),
(139, 'Jagermeister', 5),
(140, 'Goldschlager', 5),
(141, 'Rumple Minze', 5),
(167, 'Rothman\'s Peach Liqueur', 5),
(168, 'Fee\'s Old Fashioned Bitters', 8),
(172, 'Kalamata Olive(s)', 9),
(173, 'Green Olive Brine', 7),
(174, 'Kalamata Olive Brine', 7),
(176, 'Ginger Beer', 9),
(186, 'Mint Leaf', 9),
(187, 'Mint Sprig', 9),
(188, 'Orange Slice', 9),
(189, 'Pierre Ferrand 1850 Cognac', 5),
(190, 'Gin', 1),
(191, 'Vodka', 0),
(192, 'Bourbon', 4),
(193, 'Rye Whiskey', 4),
(194, 'Red Wine', 6),
(195, 'White Wine', 6),
(198, 'White Rum', 2),
(199, 'Tequila', 3);

-- --------------------------------------------------------

--
-- Table structure for table `rating`
--

CREATE TABLE `rating` (
  `id` int(11) NOT NULL,
  `score` int(11) DEFAULT NULL,
  `drink_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rating`
--

INSERT INTO `rating` (`id`, `score`, `drink_id`, `user_id`) VALUES
(159, 1, 156, 55),
(160, 5, 158, 55),
(162, 3, 161, 55),
(179, 4, 178, 55),
(180, 3, 175, 55),
(181, 3, 163, 55),
(182, 4, 169, 55),
(183, 5, 171, 55),
(184, 1, 164, 55),
(185, 2, 177, 55),
(197, 5, 196, 55),
(201, 1, 200, 55),
(204, 4, 158, 79),
(205, 5, 169, 79),
(206, 2, 164, 79),
(207, 5, 196, 79),
(208, 4, 175, 79),
(209, 1, 200, 79),
(210, 2, 156, 79),
(211, 2, 177, 79),
(212, 5, 161, 79),
(213, 5, 178, 79),
(214, 3, 203, 79),
(215, 4, 171, 79);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(12) NOT NULL,
  `password` varchar(255) NOT NULL,
  `my_bar` tinyblob
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `name`, `password`, `my_bar`) VALUES
(55, 'peter.lokey1@gmail.com', 'ploke', 'password', NULL),
(79, 'user@gmail.com', 'user1', 'user11', NULL),
(150, 'test@gmail.com', 'Test', 'test11', NULL),
(152, 'newuser@gmail.com', 'NewUser', 'newuser', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_drinks`
--

CREATE TABLE `user_drinks` (
  `users_id` int(11) NOT NULL,
  `drinks_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_drinks`
--

INSERT INTO `user_drinks` (`users_id`, `drinks_id`) VALUES
(55, 158);

-- --------------------------------------------------------

--
-- Table structure for table `user_my_bar`
--

CREATE TABLE `user_my_bar` (
  `users_id` int(11) NOT NULL,
  `my_bar_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_my_bar`
--

INSERT INTO `user_my_bar` (`users_id`, `my_bar_id`) VALUES
(55, 172),
(55, 165),
(55, 118),
(55, 116),
(55, 128),
(55, 60),
(55, 47),
(55, 46),
(55, 19),
(55, 20),
(55, 49),
(55, 48),
(55, 61),
(55, 57),
(55, 115),
(55, 119),
(55, 120),
(55, 24);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `drink`
--
ALTER TABLE `drink`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `drink_recipe`
--
ALTER TABLE `drink_recipe`
  ADD PRIMARY KEY (`drink_id`,`ingredient`);

--
-- Indexes for table `ingredient`
--
ALTER TABLE `ingredient`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKq222q1r4fmko638gx8bvptpq1` (`drink_id`),
  ADD KEY `FKpn05vbx6usw0c65tcyuce4dw5` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  ADD UNIQUE KEY `UK_gj2fy3dcix7ph7k8684gka40c` (`name`);

--
-- Indexes for table `user_drinks`
--
ALTER TABLE `user_drinks`
  ADD KEY `FKn73cmnt0mtsdedfegj11wixtf` (`drinks_id`),
  ADD KEY `FKsnjq1ho8e9ay7ycjmd0eg4ej8` (`users_id`);

--
-- Indexes for table `user_my_bar`
--
ALTER TABLE `user_my_bar`
  ADD KEY `FK48y6b2f50sm7x0ysdqst3wvfw` (`my_bar_id`),
  ADD KEY `FK2pcpw7k7fdtdvj9f84gv9vwji` (`users_id`);
COMMIT;
