importScripts("sql.js");

const DB_NAME = "whacook-db";
const STORE_NAME = "sqlite-store";
const KEY = "db-file";

let db = null;

async function loadFromIndexedDB() {
    return new Promise((resolve, reject) => {
        const request = indexedDB.open(DB_NAME, 1);
        request.onupgradeneeded = (event) => {
            const db = event.target.result;
            if (!db.objectStoreNames.contains(STORE_NAME)) {
                db.createObjectStore(STORE_NAME);
            }
        };
        request.onsuccess = (event) => {
            const db = event.target.result;
            const transaction = db.transaction(STORE_NAME, "readonly");
            const store = transaction.objectStore(STORE_NAME);
            const getRequest = store.get(KEY);
            getRequest.onsuccess = () => {
                resolve(getRequest.result);
            };
            getRequest.onerror = () => reject(getRequest.error);
        };
        request.onerror = () => reject(request.error);
    });
}

async function saveToIndexedDB() {
    if (!db) return;
    const data = db.export();
    return new Promise((resolve, reject) => {
        const request = indexedDB.open(DB_NAME, 1);
        request.onsuccess = (event) => {
            const idb = event.target.result;
            const transaction = idb.transaction(STORE_NAME, "readwrite");
            const store = transaction.objectStore(STORE_NAME);
            const putRequest = store.put(data, KEY);
            putRequest.onsuccess = () => resolve();
            putRequest.onerror = () => reject(putRequest.error);
        };
        request.onerror = () => reject(request.error);
    });
}

async function createDatabase() {
  let SQL = await initSqlJs({ locateFile: file => 'sql-wasm.wasm' });
  const storedData = await loadFromIndexedDB();
  if (storedData) {
      db = new SQL.Database(storedData);
  } else {
      db = new SQL.Database();
      await saveToIndexedDB();
  }
}

async function onModuleReady() {
  const data = this.data;

  switch (data && data.action) {
    case "exec":
      if (!data["sql"]) {
        throw new Error("exec: Missing query string");
      }

      const execResult = db.exec(data.sql, data.params)[0] ?? { values: [] };
      if (!data.sql.trim().toUpperCase().startsWith("SELECT")) {
          await saveToIndexedDB();
      }

      return postMessage({
        id: data.id,
        results: execResult
      });
    case "begin_transaction":
      return postMessage({
        id: data.id,
        results: db.exec("BEGIN TRANSACTION;")
      })
    case "end_transaction":
      const endResult = db.exec("END TRANSACTION;");
      await saveToIndexedDB();
      return postMessage({
        id: data.id,
        results: endResult
      })
    case "rollback_transaction":
      const rollbackResult = db.exec("ROLLBACK TRANSACTION;");
      await saveToIndexedDB();
      return postMessage({
        id: data.id,
        results: rollbackResult
      })
    default:
      throw new Error(`Unsupported action: ${data && data.action}`);
  }
}

function onError(err) {
  return postMessage({
    id: this.data.id,
    error: err
  });
}

db = null;
const sqlModuleReady = createDatabase()
self.onmessage = (event) => {
  return sqlModuleReady
    .then(onModuleReady.bind(event))
    .catch(onError.bind(event));
}

