package ch.zmotions.linkit;

import ch.zmotions.linkit.commons.dto.*;
import ch.zmotions.linkit.service.domain.PortalEOTest;
import ch.zmotions.linkit.service.domain.PortalLinkEOTest;
import ch.zmotions.linkit.service.domain.RoleEOTest;
import ch.zmotions.linkit.service.domain.UserEOTest;
import ch.zmotions.linkit.service.util.JpegCompressorTest;
import ch.zmotions.linkit.service.util.auth.AESEncryptorDecryptorTest;
import ch.zmotions.linkit.service.util.sort.SortHelperTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ChangePasswordUserDtoTest.class,
        CredentialsDtoTest.class,
        FileResponseDtoTest.class,
        PortalDtoTest.class,
        PortalLinkDtoTest.class,
        RestrictedUserDtoTest.class,
        RoleDtoTest.class,
        UserDtoTest.class,
        PortalEOTest.class,
        PortalLinkEOTest.class,
        RoleEOTest.class,
        UserEOTest.class,
        AESEncryptorDecryptorTest.class,
        JpegCompressorTest.class,
        SortHelperTest.class
})
public class UnitTestSuite {}