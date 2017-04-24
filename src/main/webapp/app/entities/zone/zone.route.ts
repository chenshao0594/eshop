import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ZoneComponent } from './zone.component';
import { ZoneDetailComponent } from './zone-detail.component';
import { ZonePopupComponent, ZoneDialogComponent } from './zone-dialog.component';
import { ZoneDeletePopupComponent } from './zone-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ZoneResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const zoneRoute: Routes = [
  {
    path: 'zone',
    component: ZoneComponent,
    resolve: {
      'pagingParams': ZoneResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.zone.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'zone/:id',
    component: ZoneDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.zone.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'zone-new',
    component: ZoneDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.zone.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'zone/:id/edit',
    component: ZoneDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.zone.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const zonePopupRoute: Routes = [
  
  
  {
    path: 'zone/:id/delete',
    component: ZoneDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.zone.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
