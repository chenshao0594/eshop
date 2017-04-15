import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { FileHistory } from './file-history.model';
import { FileHistoryPopupService } from './file-history-popup.service';
import { FileHistoryService } from './file-history.service';

@Component({
    selector: 'jhi-file-history-delete-dialog',
    templateUrl: './file-history-delete-dialog.component.html'
})
export class FileHistoryDeleteDialogComponent {

    fileHistory: FileHistory;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private fileHistoryService: FileHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['fileHistory']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fileHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fileHistoryListModification',
                content: 'Deleted an fileHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-file-history-delete-popup',
    template: ''
})
export class FileHistoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fileHistoryPopupService: FileHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.fileHistoryPopupService
                .open(FileHistoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
