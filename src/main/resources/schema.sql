create
database pocs_db
use pocs_db
create table sample
(
    current_id      SERIAL PRIMARY KEY,
    name            VARCHAR(255) not null,
    description     TEXT         not null,
    start_date_time TIMESTAMP    not null,
    end_date_time   TIMESTAMP    not null,
    status          TEXT         not null,
    version         INTEGER      not null    default 1,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    modified_at     TIMESTAMP,
    created_by      VARCHAR(255) not null    default 'reactive-pocs',
    modified_by     VARCHAR(255)
);

create
or replace function increment_version()
    returns trigger
as
$body$
begin
    new.version
:= new.version + 1;
return new;
end;
$body$
language plpgsql;

create trigger version_trigger
    before update
    on sample
    for each row execute procedure increment_version();
procedure
increment_version
(
);