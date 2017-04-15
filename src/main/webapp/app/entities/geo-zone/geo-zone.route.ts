import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { GeoZoneComponent } from './geo-zone.component';
import { GeoZoneDetailComponent } from './geo-zone-detail.component';
import { GeoZonePopupComponent, GeoZoneDialogComponent } from './geo-zone-dialog.component';
import { GeoZoneDeletePopupComponent } from './geo-zone-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class GeoZoneResolvePagingParams implements Resolve<any> {

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

export const geoZoneRoute: Routes = [
  {
    path: 'geo-zone',
    component: GeoZoneComponent,
    resolve: {
      'pagingParams': GeoZoneResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.geoZone.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'geo-zone-new',
    component: GeoZoneDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.geoZone.home.title'
    },
  }, {
    path: 'geo-zone/:id',
    component: GeoZoneDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.geoZone.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const geoZonePopupRoute: Routes = [
  {
    path: 'geo-zone/:id/edit',
    component: GeoZonePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.geoZone.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'geo-zone/:id/delete',
    component: GeoZoneDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.geoZone.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
