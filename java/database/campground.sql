
INSERT INTO reservation (site_id, name, from_date, to_date) VALUES (1, 'AAA', '2020-08-20', '2020-08-21');
 
 

select * from reservation where name = 'BBB';

SELECT d.park_id, s.campground_id , s.site_id, count(*) AS the_count
from reservation r
JOIN site s on r.site_id = s.site_id
JOIN campground c on s.campground_id = c.campground_id
JOIN park d on c.park_id = d.park_id
WHERE
c.campground_id = 1
AND s.site_id NOT IN (
SELECT site_id FROM reservation
WHERE (from_date, to_date)
OVERLAPS (DATE '2020-09-16', DATE '2020-09-22')
)
GROUP BY d.park_id, s.campground_id, s.site_id
ORDER BY the_count DESC
LIMIT 5;


SELECT r.site_id
from reservation r 
JOIN site s on r.site_id = s.site_id 
JOIN campground c on s.campground_id = c.campground_id 
WHERE s.campground_id = 1 AND (s.site_id NOT IN (SELECT r.site_id FROM reservation WHERE (from_date, to_date)
OVERLAPS (DATE '2020-01-01', DATE '2020-09-22'))) GROUP BY r.site_id, s.site_id;


SELECT * from reservation where name = 'AAA';

DELETE  from reservation where name = 'AAA';


SELECT * FROM reservation R
JOIN site S ON S.site_id = R.site_id
JOIN campground C ON c.campground_id = S.campground_id
where C.name = 'Canyon Wren Group Site';


-- top 5 popular
SELECT site.site_id, site.site_number, max_occupancy, accessible, max_rv_length, utilities,
COUNT(reservation_id ) AS reservation_count from site
JOIN reservation ON reservation.site_id = site.site_id
JOIN campground ON site.campground_id = campground.campground_id
WHERE campground.campground_id = 1 AND (site.site_id NOT IN (SELECT reservation.site_id FROM reservation WHERE (from_date, to_date)
OVERLAPS (DATE '2020-06-01', DATE '2020-06-10' )))
GROUP BY site.site_id
ORDER BY reservation_count DESC
LIMIT 5;