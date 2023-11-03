create
database pocs_db
use pocs_db
create table sample
(
    current_id      SERIAL PRIMARY KEY,
    name            varchar(255) not null,
    description     text         not null,
    start_date_time timestamp    not null,
    end_date_time   timestamp    not null,
    status          text         not null,
    version         integer not null default 1,
    created_at      timestamp    not null,
    modified_at     timestamp    not null,
    created_by   varchar(255) not null,
    modified_by  varchar(255)
);

create or replace function increment_version()
    returns trigger
as
$body$
begin
    new.version := new.version + 1;
return new;
end;
$body$
language plpgsql;

create trigger version_trigger
    before update on sample
    for each row execute procedure increment_version();rocedure increment_version();