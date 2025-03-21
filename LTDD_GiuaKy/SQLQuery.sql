-- Tạo cơ sở dữ liệu
CREATE DATABASE FastFoodStore;
GO

USE FastFoodStore;
GO

-- Tạo bảng User
CREATE TABLE [users] (
    userId INT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(50) NOT NULL,
    email NVARCHAR(100) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    image NVARCHAR(255)
);
GO

-- Tạo bảng Category
CREATE TABLE categories (
    categoryId INT PRIMARY KEY IDENTITY(1,1),
    categoryName NVARCHAR(100) NOT NULL,
    image NVARCHAR(255)
);
GO

-- Tạo bảng Product
CREATE TABLE products (
    productId INT PRIMARY KEY IDENTITY(1,1),
    productName NVARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image NVARCHAR(255), -- Lưu đường dẫn ảnh
    createdDate DATETIME DEFAULT GETDATE(),
    categoryId INT FOREIGN KEY REFERENCES categories(categoryId)
);
GO

-- Thêm dữ liệu mẫu vào bảng categories
INSERT INTO categories (categoryName, image)
VALUES
    (N'Thức Ăn Nhanh', 'img_fastfood'),
    (N'Đồ Uống', 'img_beverages'),
    (N'Món Tráng Miệng', 'img_desserts'),
    (N'Salad', 'img_salads');

-- Thêm dữ liệu mẫu vào bảng products
INSERT INTO products (productName, price, image, categoryId)
VALUES
    -- Sản phẩm thuộc danh mục 'Thức Ăn Nhanh'
    (N'Bánh Mì Kẹp Phô Mai', 140000, 'img_cheeseburger', 1),
    (N'Gà Viên Chiên', 155000, 'img_chicken_nuggets', 1),
    (N'Khoai Tây Chiên', 70000, 'img_french_fries', 1),
    (N'Xúc Xích', 115000, 'img_hot_dog', 1),

    -- Sản phẩm thuộc danh mục 'Đồ Uống'
    (N'Coca Cola', 47000, 'img_coca_cola', 2),
    (N'Pepsi', 45000, 'img_pepsi', 2),
    (N'Nước Cam', 83000, 'img_orange_juice', 2),
    (N'Sữa Lắc', 101000, 'img_milkshake', 2),

    -- Sản phẩm thuộc danh mục 'Món Tráng Miệng'
    (N'Bánh Socola', 83000, 'img_chocolate_cake', 3),
    (N'Kem Ly', 107000, 'img_ice_cream_sundae', 3),
    (N'Bánh Brownie', 71000, 'img_brownie', 3),

    -- Sản phẩm thuộc danh mục 'Salad'
    (N'Salad Caesar', 140000, 'img_caesar_salad', 4);

