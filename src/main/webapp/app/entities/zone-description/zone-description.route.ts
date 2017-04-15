import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ZoneDescriptionComponent } from './zone-description.component';
import { ZoneDescriptionDetailComponent } from './zone-description-detail.component';
import { ZoneDescriptionPopupComponent, ZoneDescriptionDialogComponent } from './zone-description-dialog.component';
import { ZoneDescriptionDeletePopupComponent } from './zone-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ZoneDescriptionResolvePagingParams implements Resolve<any> {

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

export const zoneDescriptionRoute: Routes = [
  {
    path: 'zone-description',
    component: ZoneDescriptionComponent,
    resolve: {
      'pagingParams': ZoneDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.zoneDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'zone-description-new',
    component: ZoneDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.zoneDescription.home.title'
    },
  }, {
    path: 'zone-description/:id',
    component: ZoneDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.zoneDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const zoneDescriptionPopupRoute: Routes = [
  {
    path: 'zone-description/:id/edit',
    component: ZoneDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.zoneDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'zone-description/:id/delete',
    component: ZoneDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.zoneDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
