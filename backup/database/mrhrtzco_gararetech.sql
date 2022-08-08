-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Waktu pembuatan: 08 Agu 2022 pada 09.09
-- Versi server: 10.3.32-MariaDB-cll-lve
-- Versi PHP: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mrhrtzco_gararetech`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `cinemas_bug_report`
--

CREATE TABLE `cinemas_bug_report` (
  `report_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `email` varchar(255) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `cinemas_bug_report`
--

INSERT INTO `cinemas_bug_report` (`report_id`, `created_at`, `email`, `description`) VALUES
(1, '2022-08-06 16:28:20', '1', 'Ada bug pas double klik tab di dashboard yach...'),
(2, '2022-08-07 12:47:00', '1', 'pala devsnya ngebug'),
(3, '2022-08-07 17:28:42', '', '');

-- --------------------------------------------------------

--
-- Struktur dari tabel `cinemas_orders`
--

CREATE TABLE `cinemas_orders` (
  `order_id` varchar(15) NOT NULL,
  `order_status` varchar(20) DEFAULT NULL,
  `show_time` int(11) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `studio_id` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `movie_id` varchar(30) DEFAULT NULL,
  `movie_status` varchar(20) DEFAULT NULL,
  `regular_ticket` int(11) DEFAULT NULL,
  `sweetbox_ticket` int(11) DEFAULT NULL,
  `chair` varchar(225) DEFAULT NULL,
  `tax` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `cinemas_orders`
--

INSERT INTO `cinemas_orders` (`order_id`, `order_status`, `show_time`, `updated_at`, `studio_id`, `user_id`, `movie_id`, `movie_status`, `regular_ticket`, `sweetbox_ticket`, `chair`, `tax`, `total`) VALUES
('3F-ZGIYT39JXS', 'success', 1659963000, '2022-08-08 01:54:44', '15372951579620352009738185113673441286', 28, '1537295157962035200', 'active', 3, NULL, 'E5,E4,F4', 3, 123600),
('AY-SXW8BWK', 'success', 1659984600, '2022-08-08 01:51:09', '15344451332551598089738185112918466564', 28, '1534445133255159808', 'active', 2, NULL, 'D6,C5', 3, 61800),
('BL-4VIDEIQ', 'success', 1659957900, '2022-08-08 01:53:53', '15341582513172766721178839445567262720100503', 28, '1534158251317276672', 'active', 5, NULL, 'D4,D5,D6,D7,D3', 3, 206000),
('J6-GD4YRYF', 'success', 1659975900, '2022-08-08 01:51:52', '15329892844815851521178839445567262720100701', 28, '1532989284481585152', 'active', 18, NULL, 'G1,G2,G3,G4,G5,G6,G7,G8,G9,F4,F5,F6,E6,E5,E4,D4,D5,D6', 3, 741600),
('MD-KX0RA2G', 'success', 1659952800, '2022-08-08 01:51:30', '15464542631635312641178839445756006400100101', 28, '1546454263163531264', 'expired', 1, NULL, 'A5', 3, 36050);

-- --------------------------------------------------------

--
-- Struktur dari tabel `cinemas_studio`
--

CREATE TABLE `cinemas_studio` (
  `studio_id` varchar(255) NOT NULL,
  `studio_hall` varchar(2) DEFAULT NULL,
  `theater_name` varchar(100) DEFAULT NULL,
  `studio_seat` text DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `cinemas_studio`
--

INSERT INTO `cinemas_studio` (`studio_id`, `studio_hall`, `theater_name`, `studio_seat`) VALUES
('1', 'B2', 'MIKO MALL CGV', 'A2,A3'),
('15329892844815851521178839445567262720100701', 'A5', 'PARIS VAN JAVA CGV', '\"G1\",\"G2\",\"G3\",\"G4\",\"G5\",\"G6\",\"G7\",\"G8\",\"G9\",\"F4\",\"F5\",\"F6\",\"E6\",\"E5\",\"E4\",\"D4\",\"D5\",\"D6\"'),
('15341582513172766721178839445567262720100503', 'C7', 'PARIS VAN JAVA CGV', '\"D4\",\"D5\",\"D6\",\"D7\",\"D3\"'),
('15344451332551598089738185112918466564', 'E4', 'BRAGA XXI', '\"D6\",\"C5\"'),
('15372951579620352009738185113673441286', 'D3', 'CIWALK XXI', '\"E5\",\"E4\",\"F4\"'),
('15464542631635312641178839445646954496100105', 'B1', '23 PASKAL SHOPPING CENTER CGV', '\"D6\",\"D5\",\"C4\",\"G3\",\"C6\",\"C5\"'),
('15464542631635312641178839445756006400100101', 'C2', 'KINGS SHOPPING CENTER CGV', '\"A5\",\"D8\",\"D9\",\"C9\",\"F8\",\"G8\",\"B1\",\"B2\",\"D3\",\"D4\",\"D2\",\"C5\",\"C6\",\"F7\",\"F6\",\"F4\",\"F3\",\"G5\",\"F5\",\"G1\",\"G2\",\"G3\"'),
('2', 'B3', '23 PASKAL SHOPPING CENTER CGV', '');

-- --------------------------------------------------------

--
-- Struktur dari tabel `cinemas_users`
--

CREATE TABLE `cinemas_users` (
  `user_id` int(11) NOT NULL,
  `city_id` varchar(50) NOT NULL DEFAULT 'JAKARTA',
  `email` varchar(30) DEFAULT NULL,
  `password` varchar(225) DEFAULT NULL,
  `image` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `cinemas_users`
--

INSERT INTO `cinemas_users` (`user_id`, `city_id`, `email`, `password`, `image`) VALUES
(1, 'JAKARTA', 'testing.email.123@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', ''),
(17, 'BALI', 'hanifsyauqi61@gmail.com', '6583c7e40ba2eaf2e71f2ee78361778d', 'https://storage.googleapis.com/gararetech-bucket/devHanif.png'),
(26, 'JAKARTA', 'ilmi@gmail.com', '098f6bcd4621d373cade4e832627b4f6', NULL),
(27, 'JAKARTA', 'ilmi', '4804a339deeb650d4165f4a69c721fbb', NULL),
(28, 'BANDUNG', '1', 'c81e728d9d4c2f636f067f89cc14862c', 'https://storage.googleapis.com/gararetech-bucket/WhatsApp Image 2022-08-06 at 18.29.06.jpeg'),
(29, 'BALI', 'biggudicku@gmail.com', '285078cc736c735668332dba1ee7a95d', ''),
(30, 'BANDUNG', 'test@gmail.com', 'cc03e747a6afbbcbf8be7668acfebee5', ''),
(31, 'JAKARTA', '2', 'c81e728d9d4c2f636f067f89cc14862c', NULL),
(32, 'BANDUNG', 'eri', 'c4ca4238a0b923820dcc509a6f75849b', ''),
(33, 'JAKARTA', 'test', '098f6bcd4621d373cade4e832627b4f6', NULL),
(34, 'BANDUNG', 'cekmail', '202cb962ac59075b964b07152d234b70', ''),
(35, 'JAKARTA', 'erisukmawan01@gmail.com', 'c4ca4238a0b923820dcc509a6f75849b', NULL);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `cinemas_bug_report`
--
ALTER TABLE `cinemas_bug_report`
  ADD PRIMARY KEY (`report_id`);

--
-- Indeks untuk tabel `cinemas_orders`
--
ALTER TABLE `cinemas_orders`
  ADD PRIMARY KEY (`order_id`);

--
-- Indeks untuk tabel `cinemas_studio`
--
ALTER TABLE `cinemas_studio`
  ADD PRIMARY KEY (`studio_id`);

--
-- Indeks untuk tabel `cinemas_users`
--
ALTER TABLE `cinemas_users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `cinemas_bug_report`
--
ALTER TABLE `cinemas_bug_report`
  MODIFY `report_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `cinemas_users`
--
ALTER TABLE `cinemas_users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
