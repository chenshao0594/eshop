import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FileHistory } from './file-history.model';
import { FileHistoryService } from './file-history.service';
@Injectable()
export class FileHistoryPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private fileHistoryService: FileHistoryService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.fileHistoryService.find(id).subscribe((fileHistory) => {
                if (fileHistory.dateAdded) {
                    fileHistory.dateAdded = {
                        year: fileHistory.dateAdded.getFullYear(),
                        month: fileHistory.dateAdded.getMonth() + 1,
                        day: fileHistory.dateAdded.getDate()
                    };
                }
                if (fileHistory.dateDeleted) {
                    fileHistory.dateDeleted = {
                        year: fileHistory.dateDeleted.getFullYear(),
                        month: fileHistory.dateDeleted.getMonth() + 1,
                        day: fileHistory.dateDeleted.getDate()
                    };
                }
                if (fileHistory.accountedDate) {
                    fileHistory.accountedDate = {
                        year: fileHistory.accountedDate.getFullYear(),
                        month: fileHistory.accountedDate.getMonth() + 1,
                        day: fileHistory.accountedDate.getDate()
                    };
                }
                this.fileHistoryModalRef(component, fileHistory);
            });
        } else {
            return this.fileHistoryModalRef(component, new FileHistory());
        }
    }

    fileHistoryModalRef(component: Component, fileHistory: FileHistory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fileHistory = fileHistory;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
