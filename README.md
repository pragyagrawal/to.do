# Pre-work - *To.Do*

**To.Do** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item. Adding a todo widget etc.


Submitted by: **Pragya Agrawal**

Time spent: **35** hours spent in total

## User Stories

The following **required** functionality is completed:

* [ ] User can **successfully add and remove items** from the todo list, they can add priority, tags, due dates.
* [ ] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [ ] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [ ] User can search for todo
* [ ] User can swipe left or right to mark a TODO as Complete or Delete.
* [ ] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [ ] User can add proprity, tags for search and due date.
* [ ] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Tweak the style improving the UI / UX, play with colors, images or backgrounds
* [ ] UI Improved with full material design.
* [ ] User can add a todo Widget on home screen 
* [ ] Daily notifications for user on due dates.

Todo Future Improvements : The following **additional** features can be implemented in Future

* [ ] UI Can further improved. 
* [ ] New concepts like recycler view can be used.
* [ ] App architecture can be improved.
* [ ] New functionality to save todo on server using firebase.

## Play store link
https://play.google.com/store/apps/details?id=com.binarybricks.mytodolist

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/ry6JQjQ.gif' title='To.Do Video Walkthrough' width='' alt='To.Do Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Video Walkthrough Widget
Here's a walkthrough of ToDo widget:

<img src='http://i.imgur.com/ebaCpuv.gif' title='To.Do Widget Video Walkthrough' width='' alt='To.Do Widget Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).
## Notes

Describe any challenges encountered while building the app.

* Data Persistence in android is challenging, one requires to add lot of boiler plate code to make it work. 
* Adding a widget requires you to work with content providers that are challenging.
* Running a task after fix interval is challenging, you have to consider various facts like battery, performance,etc. GCM network manager solves most of this issues without an overhead.

## License

    Copyright [2016] [Pragya agrawal]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
