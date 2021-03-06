# Generated by Django 4.0.4 on 2022-04-20 06:36

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('crawling', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='AuthGroup',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=150, unique=True)),
            ],
            options={
                'db_table': 'auth_group',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='AuthGroupPermissions',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
            ],
            options={
                'db_table': 'auth_group_permissions',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='AuthPermission',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=255)),
                ('codename', models.CharField(max_length=100)),
            ],
            options={
                'db_table': 'auth_permission',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='AuthUser',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('password', models.CharField(max_length=128)),
                ('last_login', models.DateTimeField(blank=True, null=True)),
                ('is_superuser', models.IntegerField()),
                ('username', models.CharField(max_length=150, unique=True)),
                ('first_name', models.CharField(max_length=150)),
                ('last_name', models.CharField(max_length=150)),
                ('email', models.CharField(max_length=254)),
                ('is_staff', models.IntegerField()),
                ('is_active', models.IntegerField()),
                ('date_joined', models.DateTimeField()),
            ],
            options={
                'db_table': 'auth_user',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='AuthUserGroups',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
            ],
            options={
                'db_table': 'auth_user_groups',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='AuthUserUserPermissions',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
            ],
            options={
                'db_table': 'auth_user_user_permissions',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='Board',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('content', models.CharField(max_length=255)),
                ('nickname', models.CharField(max_length=255)),
                ('password', models.CharField(max_length=255)),
                ('photo', models.CharField(blank=True, max_length=255, null=True)),
                ('start_date', models.DateTimeField()),
                ('title', models.CharField(max_length=255)),
                ('update_date', models.DateTimeField(blank=True, null=True)),
            ],
            options={
                'db_table': 'board',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='BoardLikeManagement',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('is_liked', models.IntegerField(blank=True, null=True)),
            ],
            options={
                'db_table': 'board_like_management',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='Comment',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('content', models.CharField(max_length=255)),
                ('nickname', models.CharField(max_length=255)),
                ('password', models.CharField(max_length=255)),
            ],
            options={
                'db_table': 'comment',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='DjangoAdminLog',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('action_time', models.DateTimeField()),
                ('object_id', models.TextField(blank=True, null=True)),
                ('object_repr', models.CharField(max_length=200)),
                ('action_flag', models.PositiveSmallIntegerField()),
                ('change_message', models.TextField()),
            ],
            options={
                'db_table': 'django_admin_log',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='DjangoContentType',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('app_label', models.CharField(max_length=100)),
                ('model', models.CharField(max_length=100)),
            ],
            options={
                'db_table': 'django_content_type',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='DjangoMigrations',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('app', models.CharField(max_length=255)),
                ('name', models.CharField(max_length=255)),
                ('applied', models.DateTimeField()),
            ],
            options={
                'db_table': 'django_migrations',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='Event',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('date', models.DateTimeField(blank=True, null=True)),
                ('photo_path', models.CharField(blank=True, max_length=255, null=True)),
                ('title', models.CharField(blank=True, max_length=255, null=True)),
            ],
            options={
                'db_table': 'event',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='GoodsReview',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('content', models.CharField(max_length=255)),
                ('nickname', models.CharField(max_length=255)),
                ('password', models.CharField(max_length=255)),
            ],
            options={
                'db_table': 'goods_review',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='GoodsUserManagement',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('is_liked', models.IntegerField(blank=True, null=True)),
                ('is_notification', models.IntegerField(blank=True, null=True)),
            ],
            options={
                'db_table': 'goods_user_management',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='Recipe',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('content', models.CharField(blank=True, max_length=255, null=True)),
                ('nickname', models.CharField(max_length=255)),
                ('password', models.CharField(max_length=255)),
                ('photo', models.CharField(blank=True, max_length=255, null=True)),
                ('start_date', models.DateTimeField()),
                ('title', models.CharField(max_length=255)),
                ('update_date', models.DateTimeField(blank=True, null=True)),
            ],
            options={
                'db_table': 'recipe',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='RecipeGoods',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
            ],
            options={
                'db_table': 'recipe_goods',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='RecipeLikeManagement',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('is_liked', models.IntegerField(blank=True, null=True)),
            ],
            options={
                'db_table': 'recipe_like_management',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='RecipeReview',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('content', models.CharField(blank=True, max_length=255, null=True)),
                ('nickname', models.CharField(blank=True, max_length=255, null=True)),
                ('password', models.CharField(blank=True, max_length=255, null=True)),
            ],
            options={
                'db_table': 'recipe_review',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('email', models.CharField(max_length=255)),
                ('is_withdrawal', models.IntegerField(blank=True, null=True)),
                ('nickname', models.CharField(max_length=255)),
                ('password', models.CharField(max_length=255)),
            ],
            options={
                'db_table': 'user',
                'managed': False,
            },
        ),
        migrations.AlterModelOptions(
            name='goods',
            options={'managed': False},
        ),
    ]
