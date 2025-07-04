PGDMP  )                    }            aromatica_db    17.5    17.5 C    D           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            E           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            F           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            G           1262    16389    aromatica_db    DATABASE     �   CREATE DATABASE aromatica_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Colombia.1252';
    DROP DATABASE aromatica_db;
                     yohan22    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                     pg_database_owner    false            H           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                        pg_database_owner    false    4            �            1259    16509    clients    TABLE       CREATE TABLE public.clients (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    email character varying(255),
    phone character varying(30),
    address character varying(255),
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.clients;
       public         heap r       postgres    false    4            �            1259    16508    clients_id_seq    SEQUENCE     �   CREATE SEQUENCE public.clients_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.clients_id_seq;
       public               postgres    false    4    220            I           0    0    clients_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.clients_id_seq OWNED BY public.clients.id;
          public               postgres    false    219            �            1259    16583 	   inventory    TABLE     {  CREATE TABLE public.inventory (
    id bigint NOT NULL,
    product_id bigint,
    change integer NOT NULL,
    type character varying(10),
    reason character varying(100),
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT inventory_type_check CHECK (((type)::text = ANY ((ARRAY['IN'::character varying, 'OUT'::character varying])::text[])))
);
    DROP TABLE public.inventory;
       public         heap r       postgres    false    4            �            1259    16582    inventory_id_seq    SEQUENCE     �   CREATE SEQUENCE public.inventory_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.inventory_id_seq;
       public               postgres    false    230    4            J           0    0    inventory_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.inventory_id_seq OWNED BY public.inventory.id;
          public               postgres    false    229            �            1259    16568    invoices    TABLE     �   CREATE TABLE public.invoices (
    id integer NOT NULL,
    order_id integer,
    invoice_number character varying(50) NOT NULL,
    total numeric(10,2) NOT NULL,
    issued_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.invoices;
       public         heap r       postgres    false    4            �            1259    16567    invoices_id_seq    SEQUENCE     �   CREATE SEQUENCE public.invoices_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.invoices_id_seq;
       public               postgres    false    4    228            K           0    0    invoices_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.invoices_id_seq OWNED BY public.invoices.id;
          public               postgres    false    227            �            1259    16551    order_items    TABLE     �   CREATE TABLE public.order_items (
    id bigint NOT NULL,
    order_id bigint,
    product_id bigint,
    quantity integer NOT NULL,
    price numeric(10,2) NOT NULL,
    price_unit numeric(38,2),
    sub_total numeric(38,2)
);
    DROP TABLE public.order_items;
       public         heap r       postgres    false    4            �            1259    16550    order_items_id_seq    SEQUENCE     �   CREATE SEQUENCE public.order_items_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.order_items_id_seq;
       public               postgres    false    226    4            L           0    0    order_items_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.order_items_id_seq OWNED BY public.order_items.id;
          public               postgres    false    225            �            1259    16532    orders    TABLE       CREATE TABLE public.orders (
    id bigint NOT NULL,
    client_id bigint,
    user_id integer,
    total numeric(38,2) NOT NULL,
    status character varying(30) DEFAULT 'pending'::character varying,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    date date
);
    DROP TABLE public.orders;
       public         heap r       postgres    false    4            �            1259    16531    orders_id_seq    SEQUENCE     �   CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.orders_id_seq;
       public               postgres    false    4    224            M           0    0    orders_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;
          public               postgres    false    223            �            1259    16521    products    TABLE     3  CREATE TABLE public.products (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    price numeric(38,2) NOT NULL,
    stock integer DEFAULT 0,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    image_url character varying(255)
);
    DROP TABLE public.products;
       public         heap r       postgres    false    4            �            1259    16520    products_id_seq    SEQUENCE     �   CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.products_id_seq;
       public               postgres    false    222    4            N           0    0    products_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;
          public               postgres    false    221            �            1259    16496    users    TABLE     �  CREATE TABLE public.users (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255) DEFAULT 'user'::character varying,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    "position" character varying(255),
    address character varying(255),
    phone character varying(255)
);
    DROP TABLE public.users;
       public         heap r       postgres    false    4            �            1259    16495    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public               postgres    false    4    218            O           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public               postgres    false    217            x           2604    16600 
   clients id    DEFAULT     h   ALTER TABLE ONLY public.clients ALTER COLUMN id SET DEFAULT nextval('public.clients_id_seq'::regclass);
 9   ALTER TABLE public.clients ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    219    220    220            �           2604    16726    inventory id    DEFAULT     l   ALTER TABLE ONLY public.inventory ALTER COLUMN id SET DEFAULT nextval('public.inventory_id_seq'::regclass);
 ;   ALTER TABLE public.inventory ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    230    229    230            �           2604    16571    invoices id    DEFAULT     j   ALTER TABLE ONLY public.invoices ALTER COLUMN id SET DEFAULT nextval('public.invoices_id_seq'::regclass);
 :   ALTER TABLE public.invoices ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    227    228    228            �           2604    16667    order_items id    DEFAULT     p   ALTER TABLE ONLY public.order_items ALTER COLUMN id SET DEFAULT nextval('public.order_items_id_seq'::regclass);
 =   ALTER TABLE public.order_items ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    225    226    226            }           2604    16696 	   orders id    DEFAULT     f   ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);
 8   ALTER TABLE public.orders ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    223    224    224            z           2604    16642    products id    DEFAULT     j   ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);
 :   ALTER TABLE public.products ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    221    222    222            u           2604    16624    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    217    218    218            7          0    16509    clients 
   TABLE DATA           N   COPY public.clients (id, name, email, phone, address, created_at) FROM stdin;
    public               postgres    false    220   O       A          0    16583 	   inventory 
   TABLE DATA           U   COPY public.inventory (id, product_id, change, type, reason, created_at) FROM stdin;
    public               postgres    false    230   �O       ?          0    16568    invoices 
   TABLE DATA           R   COPY public.invoices (id, order_id, invoice_number, total, issued_at) FROM stdin;
    public               postgres    false    228   #P       =          0    16551    order_items 
   TABLE DATA           g   COPY public.order_items (id, order_id, product_id, quantity, price, price_unit, sub_total) FROM stdin;
    public               postgres    false    226   @P       ;          0    16532    orders 
   TABLE DATA           Y   COPY public.orders (id, client_id, user_id, total, status, created_at, date) FROM stdin;
    public               postgres    false    224   ]P       9          0    16521    products 
   TABLE DATA           ^   COPY public.products (id, name, description, price, stock, created_at, image_url) FROM stdin;
    public               postgres    false    222   zP       5          0    16496    users 
   TABLE DATA           h   COPY public.users (id, name, email, password, role, created_at, "position", address, phone) FROM stdin;
    public               postgres    false    218   <Q       P           0    0    clients_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.clients_id_seq', 6, true);
          public               postgres    false    219            Q           0    0    inventory_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.inventory_id_seq', 3, true);
          public               postgres    false    229            R           0    0    invoices_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.invoices_id_seq', 1, false);
          public               postgres    false    227            S           0    0    order_items_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.order_items_id_seq', 1, false);
          public               postgres    false    225            T           0    0    orders_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.orders_id_seq', 1, false);
          public               postgres    false    223            U           0    0    products_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.products_id_seq', 1, true);
          public               postgres    false    221            V           0    0    users_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.users_id_seq', 15, true);
          public               postgres    false    217            �           2606    16623    clients clients_email_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_email_key UNIQUE (email);
 C   ALTER TABLE ONLY public.clients DROP CONSTRAINT clients_email_key;
       public                 postgres    false    220            �           2606    16602    clients clients_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.clients DROP CONSTRAINT clients_pkey;
       public                 postgres    false    220            �           2606    16728    inventory inventory_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.inventory DROP CONSTRAINT inventory_pkey;
       public                 postgres    false    230            �           2606    16576 $   invoices invoices_invoice_number_key 
   CONSTRAINT     i   ALTER TABLE ONLY public.invoices
    ADD CONSTRAINT invoices_invoice_number_key UNIQUE (invoice_number);
 N   ALTER TABLE ONLY public.invoices DROP CONSTRAINT invoices_invoice_number_key;
       public                 postgres    false    228            �           2606    16574    invoices invoices_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.invoices
    ADD CONSTRAINT invoices_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.invoices DROP CONSTRAINT invoices_pkey;
       public                 postgres    false    228            �           2606    16669    order_items order_items_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.order_items DROP CONSTRAINT order_items_pkey;
       public                 postgres    false    226            �           2606    16698    orders orders_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_pkey;
       public                 postgres    false    224            �           2606    16644    products products_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.products DROP CONSTRAINT products_pkey;
       public                 postgres    false    222            �           2606    16640    users users_email_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);
 ?   ALTER TABLE ONLY public.users DROP CONSTRAINT users_email_key;
       public                 postgres    false    218            �           2606    16626    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public                 postgres    false    218            �           1259    16734    idx_inventory_product_id    INDEX     T   CREATE INDEX idx_inventory_product_id ON public.inventory USING btree (product_id);
 ,   DROP INDEX public.idx_inventory_product_id;
       public                 postgres    false    230            �           1259    16685    idx_order_items_order_id    INDEX     T   CREATE INDEX idx_order_items_order_id ON public.order_items USING btree (order_id);
 ,   DROP INDEX public.idx_order_items_order_id;
       public                 postgres    false    226            �           1259    16714    idx_orders_client_id    INDEX     L   CREATE INDEX idx_orders_client_id ON public.orders USING btree (client_id);
 (   DROP INDEX public.idx_orders_client_id;
       public                 postgres    false    224            �           2606    16735 #   inventory inventory_product_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id);
 M   ALTER TABLE ONLY public.inventory DROP CONSTRAINT inventory_product_id_fkey;
       public               postgres    false    222    230    4751            �           2606    16699    invoices invoices_order_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.invoices
    ADD CONSTRAINT invoices_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);
 I   ALTER TABLE ONLY public.invoices DROP CONSTRAINT invoices_order_id_fkey;
       public               postgres    false    4754    228    224            �           2606    16704 %   order_items order_items_order_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON DELETE CASCADE;
 O   ALTER TABLE ONLY public.order_items DROP CONSTRAINT order_items_order_id_fkey;
       public               postgres    false    224    4754    226            �           2606    16675 '   order_items order_items_product_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id);
 Q   ALTER TABLE ONLY public.order_items DROP CONSTRAINT order_items_product_id_fkey;
       public               postgres    false    226    222    4751            �           2606    16715    orders orders_client_id_fkey    FK CONSTRAINT        ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.clients(id);
 F   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_client_id_fkey;
       public               postgres    false    4749    224    220            �           2606    16627    orders orders_user_id_fkey    FK CONSTRAINT     y   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 D   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_user_id_fkey;
       public               postgres    false    224    4745    218            7      x�3���L/M�Qp�K):��X�%U�1�4�X�7��$3/��3� %5$쐞�����������|xsQJ~R����o~^Ij�� �O~Qfr"�������������������������1PԔ+F��� (�'�      A   o   x�3�4�45�������,�L�Q.�O��4202�50�5�T04�26�2��333��50�2��5���N�IU(�WH.-.��M-«���d�3T�BQjIiQ^M1z\\\ ��&�      ?      x������ � �      =      x������ � �      ;      x������ � �      9   �   x�-��
�0@�u�Y�K��CA���v)��m�C�Լ���*���W��nM��K�)��$�����Bӿ���[��^-1� D�7�b0`bDE?R�WR)9d�����04m�ޝ7%�y�B����6�ѕ��}�(ޖ�E��	�����$5[^�g�q�Y�V�a�g�;�zy��:F      5   �  x�mѽn�0�z
^M�)J��H1lG��Ԗ��1i[�$��h��Q�N}�X�@[��w�����/��.ڮR7@\�+��Jt�N�h�+0"b��N������j�o�n��0C����[�":	8e�������IFR ?�g	 &ac�cb�#��r]��<$?����(j�/���nT��/-E��z���(�?i+�y��CHvt���v��L�D̃�~�:�a����0sK��c>�T��	��&Y���ϴ<ʐ�P��m�šW%�kٜ��0T�?����h��Vo��� ����2���B].git�o��i�n�n�:��:�i����o�����ϫ;'��z{�#n���_������Q�����N5�o�>���'�E��]�Pf< �0�Ȅ�h     