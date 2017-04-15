import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { GeoZoneDescriptionComponent } from './geo-zone-description.component';
import { GeoZoneDescriptionDetailComponent } from './geo-zone-description-detail.component';
import { GeoZoneDescriptionPopupComponent, GeoZoneDescriptionDialogComponent } from './geo-zone-description-dialog.component';
import { GeoZoneDescriptionDeletePopupComponent } from './geo-zone-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class GeoZoneDescriptionResolvePagingParams implements Resolve<any> {

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

export const geoZoneDescriptionRoute: Routes = [
  {
    path: 'geo-zone-description',
    component: GeoZoneDescriptionComponent,
    resolve: {
      'pagingParams': GeoZoneDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.geoZoneDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'geo-zone-description-new',
    component: GeoZoneDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.geoZoneDescription.home.title'
    },
  }, {
    path: 'geo-zone-description/:id',
    component: GeoZoneDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.geoZoneDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const geoZoneDescriptionPopupRoute: Routes = [
  {
    path: 'geo-zone-description/:id/edit',
    component: GeoZoneDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.geoZoneDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'geo-zone-description/:id/delete',
    component: GeoZoneDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.geoZoneDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
