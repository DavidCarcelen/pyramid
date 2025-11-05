-- ===========================================
-- CLEAN DATABASE BEFORE INSERTING INITIAL DATA
-- ===========================================

-- Disable referential integrity temporarily (only if needed)
-- SET session_replication_role = 'replica';

-- Clean tables (CASCADE ensures dependent data is also deleted)
TRUNCATE TABLE registration, tournament, stores, players RESTART IDENTITY CASCADE;

-- Re-enable referential integrity
-- SET session_replication_role = 'origin';

-- ===========================================
-- INSERT INITIAL DATA
-- ===========================================

-- STORES
INSERT INTO stores (id, email, password, nickname, address)
VALUES
    ('7b6f44b5-9c28-4a45-bc26-67c2f5d43101', 'serpi@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'Serpi', 'ali bei'),
    ('9b1a37ef-4b7a-4125-b589-94c79bdbbb02', 'pasku@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'Pasku', 'industria'),
    ('de4e8a9c-0463-4c7e-b5a7-589d08c15a03', 'hamelin@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'Hamelin', 'mataro'),
    ('f8e3d67a-918c-45a8-8bbf-82d1d0d45c04', 'elnucli@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'ElNucli', 'sants'),
    ('c1a92c11-63d2-4e76-9fdc-c0d82e4dcf05', 'magicbcn@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'MagicBCN', 'ronda sant Pau');

-- PLAYERS

INSERT INTO players (id, email, password, nickname)
VALUES
    ('a1f6a9e0-3d47-4a3a-80e1-3b44f9baf111', 'victor@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'Victor'),
    ('b2a7b9f1-2c58-4b2b-b9d2-1a22f8baf222', 'miki@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'Miki'),
    ('c3b8c1a2-1d69-4c3c-9ae3-2b33f9caf333', 'pedro@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'Pedro'),
    ('d4c9d2b3-0e7a-4d4d-a8f4-3c44f0dbf444', 'deko@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'Deko'),
    ('e5dae3c4-ff8b-4e5e-b9f5-4d55f1ecf555', 'xavi@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'Xavi'),
    ('9b2a4d8e-1d3c-4d7f-9a76-6b2c9e4cbb12', 'fran@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'Fran'),
    ('f4e7c2a9-72b8-4b0c-a1df-5e8a9cc4de77', 'juan@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'Juan'),
    ('c7a9b5f1-2f44-49e2-b3ad-2f69e91a8c33', 'palmagema@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'Palmagema'),
    ('1e4f6c9d-8b2a-4f2e-9b33-7d4a1f5a9d60', 'pegaso@example.com', '$2a$10$XY8nPLIhCQRsdg6kxb8Zp.2UHyXmh6QxJ3VLLJQopKvN4u2jRnFBi', 'Pegaso');

-- TOURNAMENTS
INSERT INTO tournament (
    id,
    tournament_name,
    start_date_time,
    max_players,
    format,
    extra_info,
    price,
    prize_money,
    organizer_id,
    companion_code,
    open_tournament,
    full_tournament,
    finished
)
VALUES
    ('11111111-aaaa-4aaa-baaa-aaaaaaaaaaaa',
     'Friday Night Magic - Pauper',
     '2025-11-08T18:30:00',
     4,
     'Pauper',
     'Classic Friday night event. 4 rounds. Prizes for top 4 players.',
     5.00,
     0.00,
     '7b6f44b5-9c28-4a45-bc26-67c2f5d43101',
     'coMP4Ni0n',
     true,
     false,
     false),

    ('22222222-bbbb-4bbb-bbbb-bbbbbbbbbbbb',
     'Saturday Commander Clash',
     '2025-11-09T17:00:00',
     24,
     'Commander',
     'Bring your best decks! Multiplayer pods, casual format.',
     10.00,
     0.00,
     '9b1a37ef-4b7a-4125-b589-94c79bdbbb02',
     'CoDeP4skU',
     true,
     false,
     false),

    ('33333333-cccc-4ccc-bccc-cccccccccccc',
     'Modern Sunday Showdown',
     '2025-11-16T11:00:00',
     40,
     'Modern',
     'Competitive event. Swiss rounds + Top 8 cut. Store credit prizes.',
     15.00,
     0.00,
     'de4e8a9c-0463-4c7e-b5a7-589d08c15a03',
     'H4m3l1n',
     true,
     false,
     false),

    ('44444444-dddd-4ddd-bddd-dddddddddddd',
     'Legacy League Week 1',
     '2025-11-22T18:00:00',
     20,
     'Legacy',
     'First week of our monthly Legacy league. Entry includes a free drink.',
     12.00,
     0.00,
     'f8e3d67a-918c-45a8-8bbf-82d1d0d45c04',
     '3lNucl1',
     true,
     false,
     false);

     TRUNCATE TABLE registration RESTART IDENTITY CASCADE;

     -- ===========================================
     -- SAMPLE REGISTRATIONS (for Pauper tournament)
     -- ===========================================

     INSERT INTO registration (
         id,
         player_id,
         tournament_id,
         paid,
         registered_at,
         reserve_list
     )
     VALUES
         ('aaa11111-aaaa-4aaa-baaa-aaaaaaaa0001', 'a1f6a9e0-3d47-4a3a-80e1-3b44f9baf111', '11111111-aaaa-4aaa-baaa-aaaaaaaaaaaa', true,  '2025-10-01T12:00:00', false),
         ('aaa11111-aaaa-4aaa-baaa-aaaaaaaa0002', 'b2a7b9f1-2c58-4b2b-b9d2-1a22f8baf222', '11111111-aaaa-4aaa-baaa-aaaaaaaaaaaa', false, '2025-10-01T12:05:00', false),
         ('aaa11111-aaaa-4aaa-baaa-aaaaaaaa0003', 'c3b8c1a2-1d69-4c3c-9ae3-2b33f9caf333', '11111111-aaaa-4aaa-baaa-aaaaaaaaaaaa', false, '2025-10-01T12:10:00', false),
         ('aaa11111-aaaa-4aaa-baaa-aaaaaaaa0004', 'd4c9d2b3-0e7a-4d4d-a8f4-3c44f0dbf444', '11111111-aaaa-4aaa-baaa-aaaaaaaaaaaa', true,  '2025-10-01T12:15:00', false),
         ('aaa11111-aaaa-4aaa-baaa-aaaaaaaa0005', 'e5dae3c4-ff8b-4e5e-b9f5-4d55f1ecf555', '11111111-aaaa-4aaa-baaa-aaaaaaaaaaaa', false, '2025-10-01T12:20:00', true),
         ('aaa11111-aaaa-4aaa-baaa-aaaaaaaa0006', '9b2a4d8e-1d3c-4d7f-9a76-6b2c9e4cbb12', '11111111-aaaa-4aaa-baaa-aaaaaaaaaaaa', false, '2025-10-01T12:25:00', true);


