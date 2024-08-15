CREATE TABLE checkout (
             id UUID PRIMARY KEY NOT NULL DEFAULT public.uuid_generate_v4(),
             product_id UUID NOT NULL,
             status VARCHAR(50) NOT NULL,
             quantity int NOT NULL,
             customer_name VARCHAR(255) NOT NULL,
             customer_email VARCHAR(255) NOT NULL,
             delivery_address VARCHAR(255) NOT NULL,
             created_at timestamptz      NOT NULL DEFAULT now(),
             updated_at timestamptz      NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX uk_checkout_status ON checkout(id, status);
