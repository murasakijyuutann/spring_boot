package com.example.myapp.mapper;

import com.example.myapp.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    // 🔍 1. Find all users
    @Select("SELECT * FROM users")
    List<User> findAll();

    // 🔍 2. Find user by ID
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    // 🔍 3. Find users by exact name
    @Select("SELECT * FROM users WHERE name = #{name}")
    List<User> findByName(String name);

    // 🔍 4. Find users by partial name (like search)
    @Select("SELECT * FROM users WHERE name LIKE CONCAT('%', #{keyword}, '%')")
    List<User> searchByName(String keyword);

    // ➕ 5. Insert new user (auto-generates ID)
    @Insert("INSERT INTO users(name, email, password_hash, created_at, provider) " +
            "VALUES(#{name}, #{email}, #{passwordHash}, #{createdAt}, #{provider})")

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    // ✏️ 6. Update user info
    @Update("UPDATE users SET name = #{name}, email = #{email}, password_hash = #{passwordHash}, provider = #{provider} WHERE id = #{id}")
    void updateUser(User user);

    // ❌ 7. Delete user by ID
    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUserById(Long id);

    @Delete("DELETE FROM users WHERE name = #{name}")
    void deleteUserByName(String name);

    // 📧 8. Check if email exists (true/false)
    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email}")
    boolean emailExists(String email);

    // 🧾 9. Count total users
    @Select("SELECT COUNT(*) FROM users")
    int countUsers();

    // 🆕 10. Find the newest registered user
    @Select("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    User findLatestUser();

    @Select("SELECT * FROM users ORDER BY id")
    List<User> findAllOrderedById();

    // 🔀 11. Find all users ordered by name
    @Select("SELECT * FROM users ORDER BY name ASC")
    List<User> findAllOrderedByName();

    // 📧 12. Find user by email (unique)
    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(String email);

    // ⏳ 13. Update only email
    @Update("UPDATE users SET email = #{email} WHERE id = #{id}")
    void updateEmail(@Param("id") Long id, @Param("email") String email);

    

}
