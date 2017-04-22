import { Route } from '@angular/router';

import { NavbarComponent, MenuComponent, SidebarComponent} from './layouts';

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
//export const sidebarRoute: Route = {
//    path: '',
//    component: SidebarComponent,
//    outlet: 'sidebar'
//};
