-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           10.4.19-MariaDB - mariadb.org binary distribution
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Copiando estrutura do banco de dados para projeto_web
CREATE DATABASE IF NOT EXISTS `projeto_web` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `projeto_web`;

-- Copiando estrutura para tabela projeto_web.log
CREATE TABLE IF NOT EXISTS `log` (
  `log_nr_id` int(11) NOT NULL AUTO_INCREMENT,
  `usu_nr_id` int(11) NOT NULL,
  `log_tx_execucao` varchar(500) NOT NULL,
  `log_dt_execucao` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`log_nr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela projeto_web.perfil
CREATE TABLE IF NOT EXISTS `perfil` (
  `per_nr_id` int(11) NOT NULL AUTO_INCREMENT,
  `per_tx_nome` varchar(50) NOT NULL,
  `per_tx_status` char(1) NOT NULL,
  PRIMARY KEY (`per_nr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela projeto_web.servico
CREATE TABLE IF NOT EXISTS `servico` (
  `ser_nr_id` int(11) NOT NULL AUTO_INCREMENT,
  `sis_nr_id` int(11) NOT NULL,
  `ser_tx_nome` varchar(45) NOT NULL,
  `ser_tx_url` varchar(45) NOT NULL,
  `ser_tx_status` char(1) NOT NULL,
  PRIMARY KEY (`ser_nr_id`),
  KEY `FK_servico_sistema` (`sis_nr_id`),
  CONSTRAINT `FK_servico_sistema` FOREIGN KEY (`sis_nr_id`) REFERENCES `sistema` (`sis_nr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela projeto_web.sistema
CREATE TABLE IF NOT EXISTS `sistema` (
  `sis_nr_id` int(11) NOT NULL AUTO_INCREMENT,
  `sis_tx_nome` varchar(50) NOT NULL,
  `sis_tx_status` char(1) NOT NULL,
  PRIMARY KEY (`sis_nr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela projeto_web.transacao
CREATE TABLE IF NOT EXISTS `transacao` (
  `tra_nr_id` int(11) NOT NULL AUTO_INCREMENT,
  `per_nr_id` int(11) NOT NULL,
  `ser_nr_id` int(11) NOT NULL,
  `tra_tx_nome` varchar(50) NOT NULL,
  `tra_tx_status` char(1) NOT NULL,
  `tra_tx_url` varchar(1000) NOT NULL,
  PRIMARY KEY (`tra_nr_id`),
  KEY `FK_transacao_perfil` (`per_nr_id`),
  KEY `FK_transacao_servico` (`ser_nr_id`),
  CONSTRAINT `FK_transacao_perfil` FOREIGN KEY (`per_nr_id`) REFERENCES `perfil` (`per_nr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_transacao_servico` FOREIGN KEY (`ser_nr_id`) REFERENCES `servico` (`ser_nr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela projeto_web.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `usu_nr_id` int(11) NOT NULL AUTO_INCREMENT,
  `per_nr_id` int(11) NOT NULL DEFAULT 1,
  `usu_tx_nome` varchar(100) NOT NULL,
  `usu_tx_login` varchar(50) NOT NULL,
  `usu_tx_senha` varchar(500) NOT NULL,
  `usu_tx_status` char(1) NOT NULL DEFAULT 'A',
  `usu_dt_cadastro` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `usu_tx_token` varchar(1000) NOT NULL,
  PRIMARY KEY (`usu_nr_id`),
  UNIQUE KEY `usu_tx_login` (`usu_tx_login`),
  KEY `FK_usuario_perfil` (`per_nr_id`),
  CONSTRAINT `FK_usuario_perfil` FOREIGN KEY (`per_nr_id`) REFERENCES `perfil` (`per_nr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Exportação de dados foi desmarcado.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
