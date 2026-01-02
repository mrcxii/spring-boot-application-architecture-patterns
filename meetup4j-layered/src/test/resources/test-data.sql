DELETE FROM event_registrations;
DELETE FROM events;

-- Insert sample events
INSERT INTO events (id, code, title, description, start_datetime, end_datetime,
                    event_type, status, venue, virtual_link,
                    ticket_price, capacity, created_at)
VALUES
-- Technology events
(1, '0NZZ6KKTZY98K','Spring Boot 4.0 Workshop',
 'Hands-on workshop covering the latest features in Spring Boot 4.0, including virtual threads, observability improvements, and new testing capabilities.',
 CURRENT_DATE + INTERVAL '7 days', CURRENT_DATE + INTERVAL '9 days',  'ONLINE', 'PUBLISHED', null, 'https://zoom.us/j/123456789',
 29.99, 100, CURRENT_TIMESTAMP),

(2, '0NZZ6S9RZYBRM','React 19 - What''s New',
 'Deep dive into React 19 features including Server Components, improved hooks, and performance optimizations.',
 CURRENT_DATE + INTERVAL '12 days', CURRENT_DATE + INTERVAL '13 days',  'ONLINE', 'PUBLISHED', null, 'https://meet.google.com/abc-defg-hij',
 0.00,  150, CURRENT_TIMESTAMP),

(3, '0NZZ6TJ53YAN8','Cloud Native Microservices Summit',
 'Annual conference on cloud-native architectures, Kubernetes, service mesh, and observability patterns.',
 CURRENT_DATE + INTERVAL '14 days', CURRENT_DATE + INTERVAL '15 days',  'OFFLINE', 'PUBLISHED', '123 Convention Center Drive, San Francisco, California, USA', null,
 299.00, 500, CURRENT_TIMESTAMP),

-- Business events
(4, '0NZZ6VDCVYASV','Startup Networking Meetup',
 'Connect with fellow entrepreneurs, investors, and innovators. Share ideas and build meaningful business relationships.',
 CURRENT_DATE + INTERVAL '15 days', CURRENT_DATE + INTERVAL '17 days',  'OFFLINE', 'PUBLISHED', '456 Innovation Hub, 2nd Floor, Austin, Texas, USA', null,
 0.00,  50, CURRENT_TIMESTAMP),

(5, '0NZZ6W78FYBHV','Product Management Masterclass',
 'Learn best practices in product strategy, roadmapping, and stakeholder management from industry experts.',
 CURRENT_DATE + INTERVAL '20 days', CURRENT_DATE + INTERVAL '21 days',  'ONLINE', 'PUBLISHED', null, 'https://zoom.us/j/987654321',
 49.99, 75, CURRENT_TIMESTAMP),

-- Health & Wellness events
(6, '0NZZ6XA1VY8X6','Morning Yoga in the Park',
 'Start your day with an energizing outdoor yoga session suitable for all skill levels.',
 CURRENT_DATE + INTERVAL '21 days', CURRENT_DATE + INTERVAL '22 days',  'OFFLINE', 'PUBLISHED', 'Central Park, North Meadow, New York, New York, USA',null,
 10.00,  30, CURRENT_TIMESTAMP),

(7, '0NZZ6Y0JBYAKS','Nutrition and Wellness Webinar',
 'Expert nutritionists discuss balanced diets, meal planning, and maintaining healthy habits.',
 CURRENT_DATE + INTERVAL '22 days', CURRENT_DATE + INTERVAL '23 days',  'ONLINE', 'PUBLISHED', null, 'https://zoom.us/j/555666777',
 0.00,  200,  CURRENT_TIMESTAMP),

-- Arts & Culture events
(8, '0NZZ6YKRZY8B8','Local Artist Exhibition Opening',
 'Celebrate the opening of our winter art exhibition featuring works from local artists.',
 CURRENT_DATE + INTERVAL '15 days', CURRENT_DATE + INTERVAL '18 days',  'OFFLINE', 'PUBLISHED', '789 Gallery Street, Seattle,Washington, USA',null,
 15.00,  100, CURRENT_TIMESTAMP),

-- Education events
(9, '0NZZ6ZF0KYB1A','Learn Python Programming - Beginner Course',
 '4-week intensive course covering Python fundamentals, data structures, and practical projects.',
 CURRENT_DATE + INTERVAL '18 days', CURRENT_DATE + INTERVAL '20 days',  'ONLINE', 'PUBLISHED', null, 'https://teams.microsoft.com/l/meetup-join/xyz',
 99.99, 40, CURRENT_TIMESTAMP),

-- Social events
(10, '0NZZ707MVYAXD','Weekend Hiking Adventure',
 'Join us for a scenic hike through the mountain trails. All fitness levels welcome!',
 CURRENT_DATE + INTERVAL '16 days', CURRENT_DATE + INTERVAL '18 days',  'OFFLINE', 'PUBLISHED', 'Mountain View Trailhead, Denver, Colorado, USA',null,
 20.00,  25, CURRENT_TIMESTAMP),

-- Food & Drink events
(11,  '0NZZ713A7YAER','Wine Tasting Experience',
 'Exclusive wine tasting event featuring premium selections from around the world.',
 CURRENT_DATE + INTERVAL '12 days', CURRENT_DATE + INTERVAL '15 days',  'OFFLINE', 'DRAFT', 'The Wine Cellar, 321 Vineyard Road, Napa, California, USA',null,
 75.00,  20, CURRENT_TIMESTAMP),

-- Entertainment events
(12, '0NZZ71YDQYADK','Live Jazz Night',
 'Enjoy an evening of smooth jazz performed by renowned local musicians.',
 CURRENT_DATE + INTERVAL '-3 days', CURRENT_DATE + INTERVAL '-2 days',  'OFFLINE', 'PUBLISHED', 'Blue Note Jazz Club, 131 W 3rd St, New York, New York, USA',null,
 25.00, 80, CURRENT_TIMESTAMP);

-- Insert sample event registrations
INSERT INTO event_registrations (id, code, event_id, event_code, attendee_email, attendee_name)
VALUES
(1, '0NZZ75Q4KY88Y',1, '0NZZ6KKTZY98K','siva@gmail.com', 'Siva'),
(2, '0NZZ76DTBYBS5',2, '0NZZ6S9RZYBRM', 'raj@gmail.com', 'Raj'),
(3, '0NZZ771D7YBJY',3, '0NZZ6TJ53YAN8','ramu@gmail.com', 'Ramu'),
(4, '0NZZ77KQ3Y92C',4, '0NZZ6VDCVYASV','neha@gmail.com', 'Neha'),
(5, '0NZZ788GFYAXB',5, '0NZZ6W78FYBHV','yuvaan@gmail.com', 'Yuvaan'),
(6, '0NZZ7920ZYAMR',6, '0NZZ6XA1VY8X6','rajesh@gmail.com', 'Rajesh'),
(7, '0NZZ79MSKYAP9',9, '0NZZ6ZF0KYB1A','thammi@gmail.com', 'Thammi'),
(8, '0NZZ7AKZBYAR7',11,'0NZZ713A7YAER', 'nikhil@gmail.com', 'Nikhil')
;

UPDATE events SET registrations_count = (SELECT COUNT(*) FROM event_registrations WHERE event_id = events.id);
