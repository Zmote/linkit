# LinkIt

A simple portal, that allows you to dynamically
define links to go to. It serves like a collection
page for various kinds of links. The idea was to 
provide a interface for common used links among a 
certain group of people. Via the Portal, all
commonly used links can be gathered and accessed at
a central place.

This project is a continuation of another one I did in 2019, [Technik Portal](https://github.com/Zmote/TechnikPortal)

# Dev
For local development, you can use following ENV properties to start:
```
DDL_AUTO=create-drop
INITIAL_ADMIN_PASS=admin
STORAGE_TYPE=system
```

## TODOs
- [ ] Upgrade Spring Framework
- [ ] Refactor Front-End JS Code
  - [x] Split into more files
  - ~~[ ] Implement smarter script loading~~
    - Will be done with VueJS Frontend
  - [x] ~~Adjust visibility of functions / encapsulate properly~~
- ~~[ ] Refactor Backend Controller Code~~
  ~~- [ ] Split into REST and Content Endpoints~~
    - Will be REST only Backend after swtich to VueJS Frontend, dropped
- [x] ~~Implement Authentication / Authorization / Login / Security~~
  - [x] ~~Entity EOs~~
  - [x] ~~UI and Controllers~~
  ~~- [x] Spring Security Settings~~
  ~~- [x] Password Security Level(Bcrypt or SHA? Or other?)~~
    ~~- Bcrypt~~
- [x] ~~Feature: Dynamic System Settings (Footer Text, Main Title)~~
- [x] ~~Feature: Grouped Display for PortalLinks~~
  - ~~Alphabetically grouping~~
- [x] ~~Feature: PortalLink Ordering (define display order)~~
  - Dropped due to alphabetically ordering providing overview
- [x] F~~eature: My Links~~
- [x] ~~Feautre: Shared Links~~
- [ ] ~~Feature: Link visibility based on assigned Profile~~
  - Profile (Access) Concept dropped
- [x] ~~Feature: Save Login / Pass for PortalLink~~
- [x] ~~Feature: Autofill Login / Pass for PortalLink~~
  - Alternativ implemented
- [ ] Feature: Import / Export PortalLinks to/from File
- [ ] Test: Write more Tests, 300 at the minimum (50/300)
- [ ] ~~Feature: Share Links globally~~
  - Dropped concept due to other alternative
- [x] ~~Remove Roles Tab frontend, only fix USER/Admin~~
- [x] ~~Simple Install Procedure for when new features implemented~~
  - Provided via Github and Heroku Combo, for local instalaltions, see Jenkins
- [ ] Auto-Deploy Mechanism
- [x] ~~Portability of DB (SQLite)~~
- [x] ~~Services cleanup: Ops that do specific tasks~~
  - ~~f.ex. which fields consist an update for a User? If a field was not changed, do we still write it to the DB?~~
  - Optimize these kinds of Ops
- [x] ~~Current User Info indicator in Header / Dropdown with Logout Button~~
- [x] ~~Enable / Disable User, 3 Login Tries, Reset Password, Forgot Password etc.~~
  - Reset and Forgot not implemented, Admin responsibility
- [ ] Implement App Logger to File /log
- [ ] Switch Frontend to Vue.js / Make Backend fully REST (post-ZIVI)
- [x] ~~Introduce Simple Category system on PortalLink, Filter like Search (Dropdown of all available categories) on front~~
- [x] ~~Portal Link Password Encryption with User Password for retrievability~~
  - Alternative implemented, Portal based key
- [x] ~~Show dialog with pass login information on clicking a link if link has password~~
  - Implemented via Modal Preview
- [ ] ~~Add login information to RDP/Teamviewer variants~~
  - Dropped due to "Show password" Alternative
- [x] ~~Introduce *Form  classes for Validation and DataTransfer Controller - View (ie. don't use EO's directly)~~
  - ~~Implemented via Facade and Dto's~~
  - ~~Define Validation on *Form Objects~~
- [x] ~~Show Passwords on Credentials page when button "show" clicked, modal (could be the same modal for links)~~
- [ ] Bulk Operations
- [ ] Add auto thumbnail retrieval on web urls
- [ ] Replace sout calls with logger, define logging strategy
- [ ] Add Auditing to critical processes
- [ ] Add Apache Guacamole for Remote Access Links

## Bugs
- [ ] New user login redirect broken behavior