import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProtectedRoutingModule} from './protected-routing.module';
import {MatButtonModule} from '@angular/material/button';
import {HomeComponent} from "./home/home.component";

@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    CommonModule,
    // Import our Routes for this module
    ProtectedRoutingModule,
    // Angular Material Imports
    MatButtonModule,
  ]
})
export class ProtectedModule { }
