import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [], // This array should be empty or contain other non-standalone components
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppComponent // Import AppComponent here because it is a standalone component
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }



