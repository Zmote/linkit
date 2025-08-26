package ch.zmotions.linkit;

import ch.zmotions.linkit.controller.AppControllerTest;
import ch.zmotions.linkit.controller.ConfigurationControllerTest;
import ch.zmotions.linkit.controller.advice.GlobalAttributesAdviceTest;
import ch.zmotions.linkit.controller.advice.GlobalExceptionHandlerAdviceTest;
import ch.zmotions.linkit.controller.auth.LoginControllerTest;
import ch.zmotions.linkit.controller.file.FileControllerTest;
import ch.zmotions.linkit.controller.file.ImagePickerControllerTest;
import ch.zmotions.linkit.controller.portal.PortalControllerTest;
import ch.zmotions.linkit.controller.portallinks.GlobalLinkControllerTest;
import ch.zmotions.linkit.controller.portallinks.PersonalLinkControllerTest;
import ch.zmotions.linkit.controller.users.ProfileControllerTest;
import ch.zmotions.linkit.controller.users.UserControllerTest;
import ch.zmotions.linkit.facade.*;
import ch.zmotions.linkit.service.impl.*;
import ch.zmotions.linkit.service.util.auth.AuthHelperTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        GlobalAttributesAdviceTest.class,
        GlobalExceptionHandlerAdviceTest.class,
        LoginControllerTest.class,
        FileControllerTest.class,
        ImagePickerControllerTest.class,
        PortalControllerTest.class,
        GlobalLinkControllerTest.class,
        PersonalLinkControllerTest.class,
        ProfileControllerTest.class,
        UserControllerTest.class,
        AppControllerTest.class,
        ConfigurationControllerTest.class,
        AuthHelperFacadeTest.class,
        PageServiceFacadeTest.class,
        PortalLinkServiceFacadeTest.class,
        PortalServiceFacadeTest.class,
        ProfileServiceFacadeTest.class,
        RoleServiceFacadeTest.class,
        UserServiceFacadeTest.class,
        AWSStorageServiceImplTest.class,
        FileSystemStorageServiceImplTest.class,
        LoginAttemptServiceImplTest.class,
        PageServiceImplTest.class,
        PortalLinkServiceImplTest.class,
        PortalServiceImplTest.class,
        ProfileServiceImplTest.class,
        RoleServiceImplTest.class,
        SecurityServiceImplTest.class,
        UserDetailsServiceImplTest.class,
        UserServiceImplTest.class,
        AuthHelperTest.class
})
public class IntegrationTestSuite {}