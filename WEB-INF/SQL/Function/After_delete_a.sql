CREATE OR REPLACE FUNCTION public.after_delete_a(
	)
    RETURNS void
    LANGUAGE 'sql'
AS $BODY$
	UPDATE Umamusume as A SET no = B.no
	FROM (SELECT ROW_NUMBER() OVER (ORDER BY no) AS no, name FROM Umamusume) AS B
	WHERE A.name = B.name;
	SELECT Setval('Seq1', (SELECT COUNT(*) FROM Umamusume));
$BODY$;