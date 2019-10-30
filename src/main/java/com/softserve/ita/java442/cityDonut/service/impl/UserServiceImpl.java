package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.user.UserEditDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserEditPasswordDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserRegistrationDto;
import com.softserve.ita.java442.cityDonut.exception.BadEmailException;
import com.softserve.ita.java442.cityDonut.exception.IncorrectPasswordException;
import com.softserve.ita.java442.cityDonut.exception.InvalidEmailException;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.user.UserEditMapper;
import com.softserve.ita.java442.cityDonut.mapper.user.UserRegistrationMapper;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.model.UserActivationRequest;
import com.softserve.ita.java442.cityDonut.repository.RoleRepository;
import com.softserve.ita.java442.cityDonut.repository.UserActivationRequestRepository;
import com.softserve.ita.java442.cityDonut.repository.UserRepository;
import com.softserve.ita.java442.cityDonut.service.UserService;
import com.softserve.ita.java442.cityDonut.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MailSenderImpl mailSender;

    @Autowired
    private UserActivationRequestRepository userActivationRequestRepository;

    @Autowired
    private UserRegistrationMapper userRegistrationMapper;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEditMapper userEditMapper;

    @Override
    public UserEditDto update(UserEditDto userEditDto) {
        User user;
        if (userEditDto != null) {
            user = userRepository
                    .findById(userEditDto.getId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + userEditDto.getId()));
        } else {
            throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID);
        }
        user.setFirstName(userEditDto.getFirstName());
        user.setLastName(userEditDto.getLastName());
        user.setEmail(userEditDto.getEmail());
        return userEditMapper.convertToDto(userRepository.save(user));
    }

    @Override
    public UserEditDto findById(long id) {
        return userEditMapper.convertToDto(userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id)));
    }

    @Override
    public void changePassword(UserEditPasswordDto userEditPasswordDto) {
        User user;
        if (userEditPasswordDto != null) {
            user = userRepository
                    .findById(userEditPasswordDto.getId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + userEditPasswordDto.getId()));
        } else {
            throw new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(userEditPasswordDto.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userEditPasswordDto.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new IncorrectPasswordException(ErrorMessage.INCORRECT_USER_PASSWORD);
        }
    }

    @Override
    public User activateUserByCode(String activationCode) {
        UserActivationRequest userActivationRequest = userActivationRequestRepository.findByActivationCode(activationCode);
        User user = userRepository.getUserById(userActivationRequest.getUserId());
        user.setStatus(User.UserStatus.ACTIVATE);

        return userRepository.save(user);
    }

    @Override
    public UserRegistrationDto registerUser(UserRegistrationDto userRegistrationDto) {

        if (!emailValidator.validateEmail(userRegistrationDto.getEmail())) {
            throw new InvalidEmailException(ErrorMessage.INVALID_EMAIL);
        }
        if (userRepository.findByEmail(userRegistrationDto.getEmail()) != null) {
            throw new BadEmailException(ErrorMessage.INCORRECT_EMAIL);
        }
        User user = userRegistrationMapper.convertToModel(userRegistrationDto);
        user.setRole(roleRepository.findByRole("user"));

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setStatus(User.UserStatus.NOT_ACTIVATE);

        user = userRepository.save(user);

        UserActivationRequest userActivationRequest = new UserActivationRequest(user.getId());
        userActivationRequestRepository.save(userActivationRequest);

        String activationCode = userActivationRequest.getActivationCode();
        String url = "localhost:8080/api/v1/registration/activate?activationCode=";
        String message = String.format("Welcome to CityDonate. To activate your account follow link: " + url + activationCode);
        mailSender.send(user.getEmail(), "Activation Code", message);

        return userRegistrationMapper.convertToDto(user);
    }
}
