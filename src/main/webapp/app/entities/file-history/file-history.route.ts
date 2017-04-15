import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FileHistoryComponent } from './file-history.component';
import { FileHistoryDetailComponent } from './file-history-detail.component';
import { FileHistoryPopupComponent, FileHistoryDialogComponent } from './file-history-dialog.component';
import { FileHistoryDeletePopupComponent } from './file-history-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class FileHistoryResolvePagingParams implements Resolve<any> {

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

export const fileHistoryRoute: Routes = [
  {
    path: 'file-history',
    component: FileHistoryComponent,
    resolve: {
      'pagingParams': FileHistoryResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.fileHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'file-history-new',
    component: FileHistoryDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.fileHistory.home.title'
    },
  }, {
    path: 'file-history/:id',
    component: FileHistoryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.fileHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fileHistoryPopupRoute: Routes = [
  {
    path: 'file-history/:id/edit',
    component: FileHistoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.fileHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'file-history/:id/delete',
    component: FileHistoryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.fileHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
