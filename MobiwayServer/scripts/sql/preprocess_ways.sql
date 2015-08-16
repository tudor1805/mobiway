
-- Clean Existing columns

ALTER TABLE ways DROP COLUMN practical_speed_forw_00;
ALTER TABLE ways DROP COLUMN practical_speed_forw_01;
ALTER TABLE ways DROP COLUMN practical_speed_forw_02;
ALTER TABLE ways DROP COLUMN practical_speed_forw_03;

ALTER TABLE ways DROP COLUMN practical_speed_forw_04;
ALTER TABLE ways DROP COLUMN practical_speed_forw_05;
ALTER TABLE ways DROP COLUMN practical_speed_forw_06;
ALTER TABLE ways DROP COLUMN practical_speed_forw_07;

ALTER TABLE ways DROP COLUMN practical_speed_forw_08;
ALTER TABLE ways DROP COLUMN practical_speed_forw_09;
ALTER TABLE ways DROP COLUMN practical_speed_forw_10;
ALTER TABLE ways DROP COLUMN practical_speed_forw_11;

ALTER TABLE ways DROP COLUMN practical_speed_forw_12;
ALTER TABLE ways DROP COLUMN practical_speed_forw_13;
ALTER TABLE ways DROP COLUMN practical_speed_forw_14;
ALTER TABLE ways DROP COLUMN practical_speed_forw_15;

ALTER TABLE ways DROP COLUMN practical_speed_forw_16;
ALTER TABLE ways DROP COLUMN practical_speed_forw_17;
ALTER TABLE ways DROP COLUMN practical_speed_forw_18;
ALTER TABLE ways DROP COLUMN practical_speed_forw_19;

ALTER TABLE ways DROP COLUMN practical_speed_forw_20;
ALTER TABLE ways DROP COLUMN practical_speed_forw_21;
ALTER TABLE ways DROP COLUMN practical_speed_forw_22;
ALTER TABLE ways DROP COLUMN practical_speed_forw_23;

-- Add new columns

ALTER TABLE ways ADD COLUMN practical_speed_forw_00 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_01 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_02 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_03 integer;

ALTER TABLE ways ADD COLUMN practical_speed_forw_04 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_05 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_06 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_07 integer;

ALTER TABLE ways ADD COLUMN practical_speed_forw_08 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_09 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_10 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_11 integer;

ALTER TABLE ways ADD COLUMN practical_speed_forw_12 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_13 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_14 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_15 integer;

ALTER TABLE ways ADD COLUMN practical_speed_forw_16 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_17 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_18 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_19 integer;

ALTER TABLE ways ADD COLUMN practical_speed_forw_20 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_21 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_22 integer;
ALTER TABLE ways ADD COLUMN practical_speed_forw_23 integer;

-- Set default values

UPDATE ways SET practical_speed_forw_00 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_01 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_02 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_03 = maxspeed_forward;

UPDATE ways SET practical_speed_forw_04 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_05 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_06 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_07 = maxspeed_forward;

UPDATE ways SET practical_speed_forw_08 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_09 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_10 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_11 = maxspeed_forward;

UPDATE ways SET practical_speed_forw_12 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_13 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_14 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_15 = maxspeed_forward;

UPDATE ways SET practical_speed_forw_16 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_17 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_18 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_19 = maxspeed_forward;

UPDATE ways SET practical_speed_forw_20 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_21 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_22 = maxspeed_forward;
UPDATE ways SET practical_speed_forw_23 = maxspeed_forward;

