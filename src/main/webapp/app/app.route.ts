import { Route } from '@angular/router';

import { NavbarComponent, MenuComponent } from './layouts';

export const navbarRoute: Route = {
    path: '',
    component: NavbarComponent,
    outlet: 'navbar'
};
export const menuRoute: Route = {
    path: '',
    component: MenuComponent,
    outlet: 'menu'
};
